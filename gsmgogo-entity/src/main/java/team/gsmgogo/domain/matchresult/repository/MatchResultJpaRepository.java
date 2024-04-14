package team.gsmgogo.domain.matchresult.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.matchresult.entity.MatchResultEntity;

public interface MatchResultJpaRepository extends JpaRepository<MatchResultEntity, Long> {
}
