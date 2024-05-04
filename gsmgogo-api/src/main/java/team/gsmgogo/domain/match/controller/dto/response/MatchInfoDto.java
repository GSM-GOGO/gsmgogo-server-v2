package team.gsmgogo.domain.match.controller.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.match.enums.MatchLevelType;
import team.gsmgogo.domain.match.enums.TeamClassType;
import team.gsmgogo.domain.team.enums.BadmintonRank;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.user.enums.GradeEnum;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MatchInfoDto {
    private Long matchId;

    private TeamType matchType;

    private MatchLevelType matchLevel;

    @JsonProperty("team_a_id")
    private Long teamAId;

    @JsonProperty("team_a_name")
    private String teamAName;

    @JsonProperty("team_a_grade")
    private GradeEnum teamAGrade;

    @JsonProperty("team_a_class_type")
    private TeamClassType teamAClassType;

    @JsonProperty("team_b_id")
    private Long teamBId;

    @JsonProperty("team_b_name")
    private String teamBName;

    @JsonProperty("team_b_grade")
    private GradeEnum teamBGrade;

    @JsonProperty("team_b_class_type")
    private TeamClassType teamBClassType;

    private BadmintonRank badmintonRank;

    @JsonProperty("badminton_a_participate_names")
    private String badmintonAParticipateNames;

    @JsonProperty("badminton_b_participate_names")
    private String badmintonBParticipateNames;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime matchStartAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime matchEndAt;

    @JsonProperty("is_vote")
    private boolean isVote;

    @JsonProperty("team_a_bet")
    private Long teamABet;

    @JsonProperty("team_b_bet")
    private Long teamBBet;

    @JsonProperty("bet_team_a_score")
    private Long betTeamAScore;

    @JsonProperty("bet_team_b_score")
    private Long betTeamBScore;

    @JsonProperty("bet_point")
    private Long betPoint;

}
