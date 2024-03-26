package team.gsmgogo.domain.normalteamparticipate.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.normalteamparticipate.enums.NormalTeamType;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.user.entity.UserEntity;

@Entity
@Table(name = "normal_team_participate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class NormalTeamParticipateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "normal_team_participate_id")
    private Long normalTeamParticipateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Enumerated(EnumType.STRING)
    @Column(name = "normal_team_type")
    private NormalTeamType normalTeamType;
}
