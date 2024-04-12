package team.gsmgogo.job;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BetResultJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job betResultJob(){
        return new JobBuilder("bet-result-Job", jobRepository)
                .start(resetCountStep(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step resetCountStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("a", jobRepository)
                .<BetEntity, BetEntity>chunk(1000, platformTransactionManager)
                .reader(itemReader())
                .build();
    }

    @Bean
    public ItemReader<BetEntity> itemReader() {
        JpaPagingItemReader<BetEntity> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT b FROM Bet b");
        return reader;
    }

}
