package team.gsmgogo.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.match.repository.MatchJpaRepository;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.job.validator.CalculateMatchValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Configuration
public class CalculateMatchResult {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final BetJpaRepository betJpaRepository;
    private final MatchJpaRepository matchJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private JobParameters jobParameters = new JobParameters();

    @Builder
    public CalculateMatchResult(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, BetJpaRepository betJpaRepository, MatchJpaRepository matchJpaRepository, UserJpaRepository userJpaRepository, JobParameters jobParameters) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.betJpaRepository = betJpaRepository;
        this.matchJpaRepository = matchJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.jobParameters = jobParameters;
    }
    
    @Autowired
    public CalculateMatchResult(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, BetJpaRepository betJpaRepository, MatchJpaRepository matchJpaRepository, UserJpaRepository userJpaRepository) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.betJpaRepository = betJpaRepository;
        this.matchJpaRepository = matchJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Bean
    public Job betJob() {
        return new JobBuilder("bet-Job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(new CalculateMatchValidator())
                .start(betStep(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    @JobScope
    public Step betStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("bet-Step", jobRepository)
                .<BetEntity, UserEntity>chunk(5, platformTransactionManager)
                .reader(betItemReader())
                .processor(betProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<BetEntity> betItemReader() {
        Long matchId = jobParameters.getLong("matchId");

        RepositoryItemReader<BetEntity> reader = new RepositoryItemReader<>();
        reader.setRepository(betJpaRepository);
        reader.setMethodName("findByMatch");
        reader.setArguments(Collections.singletonList(matchJpaRepository.findByMatchId(matchId).orElseThrow(RuntimeException::new)));
        reader.setPageSize(5);

        HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("betId", Sort.Direction.ASC);
        reader.setSort(sorts);

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<BetEntity, UserEntity> betProcessor() {

        Long matchId = jobParameters.getLong("matchId");
        Long teamAScore = jobParameters.getLong("teamAScore");
        Long teamBScore = jobParameters.getLong("teamBScore");

        return (BetEntity bet) -> {

            UserEntity user = userJpaRepository.findByUserId(bet.getUser().getUserId())
                    .orElseThrow(RuntimeException::new);

            MatchEntity match = matchJpaRepository.findByMatchId(matchId).orElseThrow(RuntimeException::new);

            String isWinTeam = teamAScore > teamBScore ? "A" : "B";
            Long winTeamAllBetPoint = isWinTeam.equals("A") ? match.getTeamABet() : match.getTeamBBet();
            Long loseTeamAllBetPoint = isWinTeam.equals("A") ? match.getTeamBBet() : match.getTeamABet();

            // 승부 예측 성공
            if (bet.getBetScoreA() > bet.getBetScoreB() && isWinTeam.equals("A") ||
                    bet.getBetScoreA() < bet.getBetScoreB() && isWinTeam.equals("B")) {

                // 스코어 예측 성공
                if (Objects.equals(Long.valueOf(bet.getBetScoreA()), teamAScore) &&
                        Objects.equals(Long.valueOf(bet.getBetScoreB()), teamBScore)) {
                    // * 1.5
                    user.addPoint(
                            (int) Math.ceil((bet.getBetPoint() * ((double) winTeamAllBetPoint / loseTeamAllBetPoint) + bet.getBetPoint()) * 1.5)
                    );
                } else {
                    // * 0.9
                    user.addPoint(
                            (int) Math.ceil((bet.getBetPoint() * ((double) winTeamAllBetPoint / loseTeamAllBetPoint) + bet.getBetPoint()) * 0.9)
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

}
