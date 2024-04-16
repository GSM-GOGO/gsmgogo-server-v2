package team.gsmgogo.domain.bet.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BetRequest {
    @NotNull
    private Long matchId;
    
    @NotNull
    private Long betPoint;

    @NotNull
    @JsonProperty("team_a_score")
    private Integer teamAScore;
    
    @NotNull
    @JsonProperty("team_b_score")
    private Integer teamBScore;
}
