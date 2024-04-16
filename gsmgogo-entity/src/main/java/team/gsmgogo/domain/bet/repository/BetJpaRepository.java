package team.gsmgogo.domain.bet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.user.entity.UserEntity;

public interface BetJpaRepository extends JpaRepository<BetEntity, Long> {
    boolean existsByUserAndMatch(UserEntity user, MatchEntity matchEntity);
}
