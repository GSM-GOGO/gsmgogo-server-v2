package team.gsmgogo.scheduler;

import lombok.RequiredArgsConstructor;
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
import team.gsmgogo.domain.buttongame.repository.ButtonGameRepository;
import team.gsmgogo.domain.buttongameparticipate.repository.ButtonGameParticipateQueryDslRepository;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.job.CalculateButtonGameJob;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ButtonGameCalculateScheduler {

    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ButtonGameRepository buttonGameRepository;
    private final UserJpaRepository userJpaRepository;
    private final ButtonGameParticipateQueryDslRepository buttonGameParticipateQueryDslRepository;

    @Scheduled(cron = "0 25 11 * * *")
    public void start() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        Map<String, JobParameter<?>> jobParametersMap = new HashMap<>();
        jobParametersMap.put("button-game-time", new JobParameter(System.currentTimeMillis(), String.class));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        jobLauncher.run(
                new CalculateButtonGameJob(
                        jobRepository,
                        platformTransactionManager,
                        buttonGameRepository,
                        userJpaRepository,
                        buttonGameParticipateQueryDslRepository).calculateButtonGameJob(),
                jobParameters
        );
    }

}
