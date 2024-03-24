package team.gsmgogo.domain.teamparticipate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.teamparticipate.entity.TeamParticipate;

public interface TeamParticipateJpaRepository extends JpaRepository<TeamParticipate, Long> {
}
