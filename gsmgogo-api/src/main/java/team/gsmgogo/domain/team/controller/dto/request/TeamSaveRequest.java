package team.gsmgogo.domain.team.controller.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.team.enums.TeamType;

import java.util.List;

@NoArgsConstructor
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeamSaveRequest {
    private String teamName;
    private TeamType teamType;
    private List<TeamParticipateDto> participates;
}
