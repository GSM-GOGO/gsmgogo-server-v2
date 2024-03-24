package team.gsmgogo.domain.matchresult.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.team.entity.TeamEntity;

@Entity
@Table(name = "match_result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class MatchResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_result_id")
    private Long matchResultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private MatchEntity match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Column(name = "team_a_score")
    private Integer teamAScore;

    @Column(name = "team_b_score")
    private Integer teamBScore;

    @Column(name = "team_a_bet")
    private Long teamABet;

    @Column(name = "team_b_bet")
    private Long teamBBet;
}
