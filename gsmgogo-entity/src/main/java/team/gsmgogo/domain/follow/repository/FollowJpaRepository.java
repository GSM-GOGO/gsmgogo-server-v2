package team.gsmgogo.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.follow.entity.FollowEntity;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {
    boolean existsByUserAndTeam(UserEntity user, TeamEntity team);

    boolean existsByUser(UserEntity user);

    Optional<FollowEntity> findByUser(UserEntity user);
}
