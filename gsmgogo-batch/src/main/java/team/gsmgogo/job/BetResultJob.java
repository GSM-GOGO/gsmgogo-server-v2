package team.gsmgogo.job;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BetResultJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final UserJpaRepository betJpaRepository;

    @Bean
    public Job betJob(){
        return new JobBuilder("bet-Job", jobRepository)
                .start(betStep(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step betStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("bet-Step", jobRepository)
                .<UserEntity, UserEntity>chunk(5, platformTransactionManager)
                .reader(betItemReader())
                .writer(new ItemWriter<UserEntity>() {
                    @Override
                    public void write(Chunk<? extends UserEntity> chunk) throws Exception {
                        chunk.forEach(System.out::println);
                    }
                })
                .build();
    }

    @Bean
    public RepositoryItemReader<UserEntity> betItemReader() {
        return new RepositoryItemReaderBuilder<UserEntity>()
                .name("betReader")
                .repository(betJpaRepository)
                .methodName("findAll")
                .build();
    }

}
