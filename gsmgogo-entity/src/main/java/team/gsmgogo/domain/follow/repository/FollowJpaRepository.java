package team.gsmgogo.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.follow.entity.FollowEntity;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {
}
