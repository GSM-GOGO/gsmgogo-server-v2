package team.gsmgogo.domain.nomalteamparticipate.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.nomalteamparticipate.enums.NomalTeamType;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.user.entity.UserEntity;

@Entity
@Table(name = "nomal_team_participate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class NomalTeamParticipateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nomal_team_participate_id")
    private Long nomalTeamParticipateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Enumerated(EnumType.STRING)
    @Column(name = "nomal_team_type")
    private NomalTeamType nomalTeamType;
}
