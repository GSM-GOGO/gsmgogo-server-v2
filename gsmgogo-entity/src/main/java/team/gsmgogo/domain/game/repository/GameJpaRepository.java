package team.gsmgogo.domain.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.game.entity.GameEntity;

public interface GameJpaRepository extends JpaRepository<GameEntity, Long> {
}
