package team.gsmgogo.domain.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import team.gsmgogo.domain.match.entity.MatchEntity;

import java.util.Optional;

public interface MatchJpaRepository extends JpaRepository<MatchEntity, Long> {
    Optional<MatchEntity> findByMatchId(Long matchId);

    @Query("SELECT SUM(match.teamABet + match.teamBBet) FROM MatchEntity match WHERE match.isEnd = false")
    Integer sumTeamAAndTeamBBetPoints();
}
