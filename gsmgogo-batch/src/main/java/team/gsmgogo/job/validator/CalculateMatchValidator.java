package team.gsmgogo.job.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.beans.factory.annotation.Value;

public class CalculateMatchValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        Long matchId = parameters.getLong("matchId");
        Long teamAScore = parameters.getLong("teamAScore");
        Long teamBScore = parameters.getLong("teamBScore");

        if (matchId == null || teamAScore == null || teamBScore == null) {
            throw new JobParametersInvalidException("not valid jobParameters");
        }
    }
}
