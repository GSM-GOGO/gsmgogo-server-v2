package team.gsmgogo.domain.teamparticipate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.teamparticipate.entity.TeamParticipateEntity;
import team.gsmgogo.domain.user.entity.UserEntity;

import java.util.Optional;

public interface TeamParticipateJpaRepository extends JpaRepository<TeamParticipateEntity, Long> {
    boolean existsByUserAndTeam(UserEntity user, TeamEntity team);
}
