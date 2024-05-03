package team.gsmgogo.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import team.gsmgogo.domain.game.repository.GameQueryDslRepository;
import team.gsmgogo.domain.user.repository.UserQueryDslRepository;

@Configuration
@RequiredArgsConstructor
public class ResetCountJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final UserQueryDslRepository userQueryDslRepository;
    private final GameQueryDslRepository gameQueryDslRepository;

    @Bean(name = "resetJob")
    public Job resetJob(){
        return new JobBuilder("reset-count-Job", jobRepository)
            .start(resetSmsCountStep(jobRepository, platformTransactionManager))
            .next(resetGameCountStep(jobRepository, platformTransactionManager))
            .build();
    }

    @Bean
    public Step resetSmsCountStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("reset-sms-count-step", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                userQueryDslRepository.bulkResetVerifyCount();
                return RepeatStatus.FINISHED;
            },
            platformTransactionManager)
            .build();
    }

    @Bean
    public Step resetGameCountStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("reset-game-count-step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                            gameQueryDslRepository.bulkResetGameCount();
                            return RepeatStatus.FINISHED;
                        },
                        platformTransactionManager)
                .build();
    }
}
