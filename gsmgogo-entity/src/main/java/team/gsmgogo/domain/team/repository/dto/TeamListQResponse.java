package team.gsmgogo.domain.team.repository.dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.match.enums.TeamClassType;
import team.gsmgogo.domain.team.enums.BadmintonRank;
import team.gsmgogo.domain.user.enums.GradeEnum;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamListQResponse {
    private Long teamId;
    private String teamName;
    @Enumerated(EnumType.STRING)
    private GradeEnum teamGrade;
    @Enumerated(EnumType.STRING)
    private TeamClassType teamClassType;
    private Integer winCount;
    private boolean isFollow;
    private boolean isMyTeam = false;
    @Nullable
    @Enumerated(EnumType.STRING)
    private BadmintonRank badmintonRank;
}
