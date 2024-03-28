package team.gsmgogo.domain.team.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeamInfoDto {
    @NotNull
    private Long teamId;

    @NotNull
    private String teamName;

    @NotNull
    private String teamGrade;

    @NotNull
    private String teamClassType;

    @NotNull
    private Integer winCount;

    @NotNull
    private boolean isFollow;

    private String badmintonRank;
}
