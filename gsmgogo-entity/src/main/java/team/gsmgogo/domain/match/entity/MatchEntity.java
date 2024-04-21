package team.gsmgogo.domain.match.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.match.enums.MatchLevelType;
import team.gsmgogo.domain.match.enums.TeamClassType;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.user.enums.GradeEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "matchs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_a")
    private TeamEntity teamA;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_b")
    private TeamEntity teamB;

    @Column(name = "team_a_bet")
    private Long teamABet;

    @Column(name = "team_b_bet")
    private Long teamBBet;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "is_end")
    private Boolean isEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_a_grade")
    private GradeEnum teamAGrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_a_class_type")
    private TeamClassType teamAClassType;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_b_grade")
    private GradeEnum teamBGrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_b_class_type")
    private TeamClassType teamBClassType;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_type")
    private TeamType matchType;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_level")
    private MatchLevelType matchLevel;

    public void end() {
        this.isEnd = true;
    }

    public void teamABetPoint(Integer point) {
        this.teamABet = this.getTeamABet() + point;
    }

    public void teamBBetPoint(Integer point) {
        this.teamBBet = this.getTeamBBet() + point;
    }
}
