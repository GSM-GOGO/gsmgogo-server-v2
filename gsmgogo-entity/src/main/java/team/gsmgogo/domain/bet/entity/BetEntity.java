package team.gsmgogo.domain.bet.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.user.entity.UserEntity;

@Entity
@Table(name = "team_participate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class BetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bet_id")
    private Long betId;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bet_user_id")
    private UserEntity user;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bet_match_id")
    private MatchEntity match;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bet_win")
    private TeamEntity team;

    @Column(name = "bet_point")
    private Long betPoint;

    @Column(name = "bet_score_a")
    private Integer betScoreA;

    @Column(name = "bet_score_b")
    private Integer betScoreB;
}
