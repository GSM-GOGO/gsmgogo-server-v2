package team.gsmgogo.global.batch.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CalculateMatchResultRequest {
    @NotNull
    private Long matchId;
    @NotNull
    private Integer teamAScore;
    @NotNull
    private Integer teamBScore;
}
