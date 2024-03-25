package team.gsmgogo.domain.bet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.bet.entity.BetEntity;

public interface BetJpaRepository extends JpaRepository<BetEntity, Long> {
}
