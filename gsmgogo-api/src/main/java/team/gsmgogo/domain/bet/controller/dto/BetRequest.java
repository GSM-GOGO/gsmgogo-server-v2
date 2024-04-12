package team.gsmgogo.domain.bet.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BetRequest {
    private Long matchId;
    private Long teamId;
    private Long betPoint;
    private Integer teamAScore;
    private Integer teamBScore;
}
