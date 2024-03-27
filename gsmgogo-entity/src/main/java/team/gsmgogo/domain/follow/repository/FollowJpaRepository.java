package team.gsmgogo.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.follow.entity.FollowEntity;
import team.gsmgogo.domain.user.entity.UserEntity;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {
    boolean existsByUser(UserEntity user);
}
