package team.gsmgogo.global.batch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.batch.core.launch.JobLauncher;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.global.batch.dto.CalculateMatchResultRequest;
import team.gsmgogo.job.CalculateMatchResult;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final BetJpaRepository betJpaRepository;

    @PostMapping("/batch/calculate-match-result")
    public ResponseEntity<Void> calculateMatchResult()
            throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        Map<String, JobParameter<?>> jobParametersMap = new HashMap<>();
        jobParametersMap.put("bet-result-time", new JobParameter(System.currentTimeMillis(), String.class));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        jobLauncher.run(new CalculateMatchResult(jobRepository, platformTransactionManager, betJpaRepository).betJob(),
                jobParameters);

        return ResponseEntity.ok().build();
    }
}
