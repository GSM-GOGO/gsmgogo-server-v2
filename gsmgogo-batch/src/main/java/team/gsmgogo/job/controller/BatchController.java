package team.gsmgogo.job.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.gsmgogo.job.controller.dto.CalculateMatchResultRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final ApplicationContext ac;

    @PostMapping("/calculate-match-result")
    public ResponseEntity<Void> calculateMatchResult(@RequestBody @Valid CalculateMatchResultRequest request)
            throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        Map<String, JobParameter<?>> jobParametersMap = new HashMap<>();
        jobParametersMap.put("bet-result-time", new JobParameter(System.currentTimeMillis(), String.class));
        jobParametersMap.put("matchId", new JobParameter<>(request.getMatchId(), Long.class));
        jobParametersMap.put("teamAScore", new JobParameter<>(request.getTeamAScore(), Long.class));
        jobParametersMap.put("teamBScore", new JobParameter<>(request.getTeamBScore(), Long.class));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        jobLauncher.run((Job) ac.getBean("calculateMatchJob"), jobParameters);

        return ResponseEntity.ok().build();
    }
}
