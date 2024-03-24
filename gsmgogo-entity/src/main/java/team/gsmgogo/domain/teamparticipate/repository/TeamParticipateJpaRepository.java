package team.gsmgogo.domain.teamparticipate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.teamparticipate.entity.TeamParticipateEntity;

public interface TeamParticipateJpaRepository extends JpaRepository<TeamParticipateEntity, Long> {
}
