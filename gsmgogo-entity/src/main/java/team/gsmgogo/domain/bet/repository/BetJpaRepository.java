package team.gsmgogo.domain.bet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.match.entity.MatchEntity;

public interface BetJpaRepository extends JpaRepository<BetEntity, Long> {
    List<BetEntity> findByUser(UserEntity user);
    boolean existsByUserAndMatch(UserEntity user, MatchEntity matchEntity);
}
