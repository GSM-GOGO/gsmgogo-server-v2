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
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ResetLoginCountJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final UserJpaRepository userJpaRepository;

    @Bean
    public Job resetCountJob(){
        return new JobBuilder("reset-count-Job", jobRepository)
            .start(resetCountStep(jobRepository, platformTransactionManager))
            .build();
    }

    @Bean
    public Step resetCountStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("reset-count-step", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                List<UserEntity> userList = userJpaRepository.findAll();
                userList.forEach(userEntity -> {
                    userEntity.resetCount();
                    userJpaRepository.save(userEntity);
                });
                log.info("w");
                return RepeatStatus.FINISHED;
            },
            platformTransactionManager)
            .build();
    }
}
