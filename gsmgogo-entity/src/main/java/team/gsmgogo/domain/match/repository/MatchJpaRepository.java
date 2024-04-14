package team.gsmgogo.domain.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.match.entity.MatchEntity;

import java.util.Optional;

public interface MatchJpaRepository extends JpaRepository<MatchEntity, Long> {
    Optional<MatchEntity> findByMatchId(Long matchId);
}
