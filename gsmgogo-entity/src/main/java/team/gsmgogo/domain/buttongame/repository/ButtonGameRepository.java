package team.gsmgogo.domain.buttongame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.buttongame.entity.ButtonGameEntity;

public interface ButtonGameRepository extends JpaRepository<ButtonGameEntity, Long> {
}
