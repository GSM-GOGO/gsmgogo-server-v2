package team.gsmgogo.scheduler;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import team.gsmgogo.domain.game.repository.GameQueryDslRepository;
import team.gsmgogo.domain.user.repository.UserQueryDslRepository;
import team.gsmgogo.job.ResetCountJob;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResetScheduler {
    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final UserQueryDslRepository userQueryDslRepository;
    private final GameQueryDslRepository gameQueryDslRepository;

//    @Scheduled(cron = "0 0 0 * * *")
//    public void reset() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
//
//        Map<String, JobParameter<?>> jobParametersMap = new HashMap<>();
//        jobParametersMap.put("reset-result-time", new JobParameter(System.currentTimeMillis(), String.class));
//        JobParameters jobParameters = new JobParameters(jobParametersMap);
//
//        jobLauncher.run(
//            new ResetCountJob(jobRepository, platformTransactionManager, userQueryDslRepository, gameQueryDslRepository).resetJob(),
//                jobParameters
//        );
//    }
}
