package team.gsmgogo.domain.team.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.user.enums.GradeEnum;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeamListResponse {
    private Long teamId;
    private String teamName;
    @Enumerated(EnumType.STRING)
    private GradeEnum teamGrade;
    @Enumerated(EnumType.STRING)
    private TeamClassType teamClassType;
    private Integer winCount;
    private boolean isFollow;
    private String badmintonRank;
}
