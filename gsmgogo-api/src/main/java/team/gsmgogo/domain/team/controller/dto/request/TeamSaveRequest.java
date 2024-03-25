package team.gsmgogo.domain.team.controller.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.team.enums.TeamType;

import java.util.List;

@NoArgsConstructor
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeamSaveRequest {
    @NotBlank
    @Size(min = 1, max = 7)
    private String teamName;
    @NotNull
    private TeamType teamType;
    @NotNull
    private List<TeamParticipateDto> participates;
}
