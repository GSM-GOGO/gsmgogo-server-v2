package team.gsmgogo.domain.match.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.match.enums.MatchLevelType;
import team.gsmgogo.domain.match.enums.TeamClassType;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.user.enums.GradeEnum;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MatchResultDto {
    private Long matchId;
    private TeamType matchType;
    private MatchLevelType matchLevel;
    private Long teamAId;
    private String teamAName;
    private GradeEnum teamAGrade;
    private TeamClassType teamAClassType;
    private Long teamBId;
    private String teamBName;
    private GradeEnum teamBGrade;
    private TeamClassType teamBClassType;
    private boolean isVote;
    private Long teamABet;
    private Long teamBBet;
    private Integer teamAScore;
    private Integer teamBScore;
    private Integer betTeamAScore;
    private Integer betTeamBScore;
    private Long earnedPoint;
    private Long losePoint;
}
