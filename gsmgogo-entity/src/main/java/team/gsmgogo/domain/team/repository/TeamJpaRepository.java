package team.gsmgogo.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.team.entity.TeamEntity;

public interface TeamJpaRepository extends JpaRepository<TeamEntity, Long> {
}
