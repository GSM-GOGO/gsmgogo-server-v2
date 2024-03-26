package team.gsmgogo.domain.team.controller.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.normalteamparticipate.enums.NormalTeamType;

import java.util.List;

@NoArgsConstructor
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeamNormalSaveRequest {
    @NotNull
    private Long userId;
    @NotNull
    private List<NormalTeamType> teamTypes;
}
