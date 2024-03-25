package team.gsmgogo.domain.matchresult.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.match.entity.MatchEntity;

public interface MatchResultJpaRepository extends JpaRepository<MatchEntity, Long> {
}
