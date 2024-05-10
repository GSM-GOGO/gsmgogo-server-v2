package team.gsmgogo.job;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.match.repository.MatchJpaRepository;
import team.gsmgogo.domain.matchresult.entity.MatchResultEntity;
import team.gsmgogo.domain.matchresult.repository.MatchResultJpaRepository;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.job.validator.CalculateMatchValidator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CalculateMatchResultJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final BetJpaRepository betJpaRepository;
    private final MatchJpaRepository matchJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final MatchResultJpaRepository matchResultJpaRepository;

    private final Integer chunkSize = 10;

    @Bean(name = "calculateMatchJob")
    public Job calculateMatchJob(@Qualifier("betStep") Step betStep, @Qualifier("migrationResultStep") Step migrationResultStep, @Qualifier("participatesAddPointStep") Step participatesAddPointStep) {
        return new JobBuilder("calculate-match-job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(new CalculateMatchValidator())
                .start(betStep)
                .on("COMPLETED").to((Step) migrationResultStep)
                .on("COMPLETED").to((Step) participatesAddPointStep)
                .end()
                .build();
    }

    @Bean
    @Qualifier("betStep")
    @JobScope
    public Step betStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, ItemReader<BetEntity> betItemReader, ItemProcessor<BetEntity, UserEntity> betProcessor){
        return new StepBuilder("calculate-match-step", jobRepository)
                .<BetEntity, UserEntity>chunk(chunkSize, platformTransactionManager)
                .reader(betItemReader)
                .processor(betProcessor)
                .writer(writer())
                .build();
    }

    @Bean
    @Qualifier("migrationResultStep")
    @JobScope
    public Step migrationResultStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, Tasklet migrationResultTasklet){
        return new StepBuilder("migration-result-step", jobRepository)
                .tasklet(migrationResultTasklet, platformTransactionManager)
                .build();
    }


    @Bean
    @Qualifier("participatesAddPointStep")
    @JobScope
    public Step participatesAddPointStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, Tasklet participatesAddPointTasklet){
        return new StepBuilder("participates-add-point-step", jobRepository)
                .tasklet(participatesAddPointTasklet, platformTransactionManager)
                .build();
    }

    // betResult

    @Bean
    @StepScope
    public ItemReader<BetEntity> betItemReader(@Value("#{jobParameters['matchId']}") Long matchId) {

        RepositoryItemReader<BetEntity> reader = new RepositoryItemReader<>();
        reader.setRepository(betJpaRepository);
        reader.setMethodName("findByMatch");
        reader.setArguments(Collections.singletonList(matchJpaRepository.findByMatchId(matchId).orElseThrow(RuntimeException::new)));
        reader.setPageSize(chunkSize);

        HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("betId", Sort.Direction.ASC);
        reader.setSort(sorts);

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<BetEntity, UserEntity> betProcessor(@Value("#{jobParameters['matchId']}") Long matchId,
                                                             @Value("#{jobParameters['teamAScore']}") Long teamAScore,
                                                             @Value("#{jobParameters['teamBScore']}") Long teamBScore) {

        return (BetEntity bet) -> {

            UserEntity user = userJpaRepository.findByUserId(bet.getUser().getUserId())
                    .orElseThrow(RuntimeException::new);

            MatchEntity match = matchJpaRepository.findByMatchId(matchId).orElseThrow(RuntimeException::new);

            if (match.getIsEnd()) {
                throw new JobExecutionException("이미 끝난 경기 입니다.");
            }

            String isWinTeam = teamAScore > teamBScore ? "A" : "B";
            Long winTeamAllBetPoint = isWinTeam.equals("A") ? match.getTeamABet() : match.getTeamBBet();
            Long loseTeamAllBetPoint = isWinTeam.equals("A") ? match.getTeamBBet() : match.getTeamABet();

            // 승부 예측 성공
            if (bet.getBetScoreA() > bet.getBetScoreB() && isWinTeam.equals("A") ||
                    bet.getBetScoreA() < bet.getBetScoreB() && isWinTeam.equals("B")) {

                // 스코어 예측 성공
                if (Objects.equals(Long.valueOf(bet.getBetScoreA()), teamAScore) &&
                        Objects.equals(Long.valueOf(bet.getBetScoreB()), teamBScore)) {
                    // * 2
                    user.addPoint(
                            (int) Math.ceil(((bet.getBetPoint() * ((double) loseTeamAllBetPoint / winTeamAllBetPoint)) * 2) + bet.getBetPoint())
                    );
                } else {
                    // * 1
                    user.addPoint(
                            (int) Math.ceil((bet.getBetPoint() * ((double) loseTeamAllBetPoint / winTeamAllBetPoint)) + bet.getBetPoint())
                    );
                }

            }

            return user;

        };
    }

    @Bean
    @StepScope
    public ItemWriter<UserEntity> writer() {
        return userJpaRepository::saveAll;
    }

    // migrationResult

    @Bean
    public Tasklet migrationResultTasklet() {
        return (contribution, chunkContext) -> {

            Long matchId = chunkContext.getStepContext().getStepExecution().getJobParameters().getLong("matchId");
            Long teamAScore = chunkContext.getStepContext().getStepExecution().getJobParameters().getLong("teamAScore");
            Long teamBScore = chunkContext.getStepContext().getStepExecution().getJobParameters().getLong("teamBScore");

            MatchEntity match = matchJpaRepository.findByMatchId(matchId).orElseThrow(RuntimeException::new);
            match.end();

            matchResultJpaRepository.save(
                    MatchResultEntity.builder()
                            .match(match)
                            .team(teamAScore > teamBScore ? match.getTeamA() : match.getTeamB())
                            .teamABet(match.getTeamABet())
                            .teamBBet(match.getTeamBBet())
                            .teamAScore(teamAScore.intValue())
                            .teamBScore(teamBScore.intValue())
                            .build()
            );
            matchJpaRepository.save(match);

            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet participatesAddPointTasklet() {
        return (contribution, chunkContext) -> {

            Long matchId = chunkContext.getStepContext().getStepExecution().getJobParameters().getLong("matchId");
            Long teamAScore = chunkContext.getStepContext().getStepExecution().getJobParameters().getLong("teamAScore");
            Long teamBScore = chunkContext.getStepContext().getStepExecution().getJobParameters().getLong("teamBScore");

            MatchEntity match = matchJpaRepository.findByMatchId(matchId).orElseThrow(RuntimeException::new);

            String isWinTeam = teamAScore > teamBScore ? "A" : "B";
            Long winTeamAllBetPoint = isWinTeam.equals("A") ? match.getTeamABet() : match.getTeamBBet();
            Long loseTeamAllBetPoint = isWinTeam.equals("A") ? match.getTeamBBet() : match.getTeamABet();

            if (isWinTeam.equals("A")) {
                match.getTeamA()
                        .getTeamParticipates().forEach(
                                participate -> {
                                    UserEntity user = participate.getUser();
                                    user.addPoint((int) Math.ceil((loseTeamAllBetPoint + winTeamAllBetPoint) * 0.025));
                                    userJpaRepository.save(user);
                                });
            } else {
                match.getTeamB()
                        .getTeamParticipates().forEach(
                                participate -> {
                                    UserEntity user = participate.getUser();
                                    user.addPoint((int) Math.ceil((loseTeamAllBetPoint + winTeamAllBetPoint) * 0.025));
                                    userJpaRepository.save(user);
                                });
            }

            return RepeatStatus.FINISHED;
        };
    }

}
