package team.gsmgogo.global.batch.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CalculateMatchResultRequest {
    private Long matchId;
    private Integer teamAScore;
    private Integer teamBScore;
}
