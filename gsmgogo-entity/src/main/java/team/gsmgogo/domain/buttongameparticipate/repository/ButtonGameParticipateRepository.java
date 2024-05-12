package team.gsmgogo.domain.buttongameparticipate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.buttongame.entity.ButtonGameEntity;
import team.gsmgogo.domain.buttongameparticipate.entity.ButtonGameParticipate;
import team.gsmgogo.domain.user.entity.UserEntity;

public interface ButtonGameParticipateRepository extends JpaRepository<ButtonGameParticipate, Long> {
    boolean existsByButtonGameAndUser(ButtonGameEntity buttonGame, UserEntity user);
}
