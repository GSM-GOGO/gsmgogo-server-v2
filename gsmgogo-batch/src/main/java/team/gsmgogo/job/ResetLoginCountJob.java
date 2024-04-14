package team.gsmgogo.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import team.gsmgogo.domain.user.repository.UserQueryDslRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ResetLoginCountJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final UserQueryDslRepository userQueryDslRepository;

    @Bean(name = "resetCountJob")
    public Job resetCountJob(){
        return new JobBuilder("reset-count-Job", jobRepository)
            .start(resetCountStep(jobRepository, platformTransactionManager))
            .build();
    }

    @Bean
    public Step resetCountStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("reset-count-step", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                userQueryDslRepository.bulkResetVerifyCount();
                return RepeatStatus.FINISHED;
            },
            platformTransactionManager)
            .build();
    }
}
