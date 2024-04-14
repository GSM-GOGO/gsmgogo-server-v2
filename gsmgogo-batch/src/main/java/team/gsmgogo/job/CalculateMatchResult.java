package team.gsmgogo.job;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
import team.gsmgogo.job.validator.CalculateMatchValidator;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
public class CalculateMatchResult {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final BetJpaRepository betJpaRepository;
    private JobParameters jobParameters;

    public CalculateMatchResult(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, BetJpaRepository betJpaRepository, JobParameters jobParameters) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.betJpaRepository = betJpaRepository;
        this.jobParameters = jobParameters;
    }

    @Autowired
    public CalculateMatchResult(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, BetJpaRepository betJpaRepository) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.betJpaRepository = betJpaRepository;
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
    public Step betStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("bet-Step", jobRepository)
                .<BetEntity, BetEntity>chunk(5, platformTransactionManager)
                .reader(betItemReader())
                .writer(writer())
                .build();
    }

    @Bean
    public RepositoryItemReader<BetEntity> betItemReader() {
        return new RepositoryItemReaderBuilder<BetEntity>()
                .name("betReader")
                .repository(betJpaRepository)
                .methodName("findAll")
                .pageSize(5)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("betId", Sort.Direction.ASC))
                .build();
    }

//    @Bean
//    public ItemProcessor<BetEntity, UserEntity> betProcessor() {
//        return (BetEntity bet) -> {
//
//
//
//        };
//    }

    @Bean
    public ItemWriter<BetEntity> writer() {
        return bets -> bets.forEach(bet -> System.out.println(jobParameters.getLong("matchId")));
    }

}
