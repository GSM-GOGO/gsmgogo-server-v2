package team.gsmgogo.domain.follow.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.user.entity.UserEntity;

@Entity
@Table(name = "follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long follwoId;

    @OneToOne
    @Column(name = "team_id")
    private TeamEntity team;

    @OneToOne
    @Column(name = "user_id")
    private UserEntity user;
}
