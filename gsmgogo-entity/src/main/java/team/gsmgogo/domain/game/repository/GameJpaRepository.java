package team.gsmgogo.domain.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.game.entity.GameEntity;
import team.gsmgogo.domain.user.entity.UserEntity;

import java.util.Optional;

public interface GameJpaRepository extends JpaRepository<GameEntity, Long> {
    Optional<GameEntity> findByUser(UserEntity user);
}
