package team.gsmgogo.job.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.domain.match.repository.MatchJpaRepository;
import team.gsmgogo.domain.matchresult.repository.MatchResultJpaRepository;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.job.CalculateMatchResultJob;
import team.gsmgogo.job.controller.dto.CalculateMatchResultRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final BetJpaRepository betJpaRepository;
    private final MatchJpaRepository matchJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final MatchResultJpaRepository matchResultJpaRepository;

    @PostMapping("/batch/calculate-match-result")
    public ResponseEntity<Void> calculateMatchResult(@RequestBody @Valid CalculateMatchResultRequest request)
            throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        Map<String, JobParameter<?>> jobParametersMap = new HashMap<>();
        jobParametersMap.put("bet-result-time", new JobParameter(System.currentTimeMillis(), String.class));
        jobParametersMap.put("matchId", new JobParameter<>(request.getMatchId(), Long.class));
        jobParametersMap.put("teamAScore", new JobParameter<>(request.getTeamAScore(), Long.class));
        jobParametersMap.put("teamBScore", new JobParameter<>(request.getTeamBScore(), Long.class));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        jobLauncher.run(CalculateMatchResultJob.builder()
                .jobRepository(jobRepository)
                .matchResultJpaRepository(matchResultJpaRepository)
                .matchJpaRepository(matchJpaRepository)
                .betJpaRepository(betJpaRepository)
                .userJpaRepository(userJpaRepository)
                .platformTransactionManager(platformTransactionManager)
                .jobParameters(jobParameters).build().betJob(), jobParameters);

        return ResponseEntity.ok().build();
    }
}
