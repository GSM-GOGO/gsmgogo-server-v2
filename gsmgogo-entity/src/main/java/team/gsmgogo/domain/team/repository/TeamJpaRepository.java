package team.gsmgogo.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.enums.ClassEnum;
import team.gsmgogo.domain.user.enums.GradeEnum;

import java.util.List;
import java.util.Optional;

public interface TeamJpaRepository extends JpaRepository<TeamEntity, Long> {
    Optional<TeamEntity> findByTeamId(Long teamId);

    List<TeamEntity> findByTeamType(TeamType teamType);

    Optional<TeamEntity> findByTeamTypeAndTeamId(TeamType teamType, Long teamId);

    boolean existsByAuthorAndTeamType(UserEntity author, TeamType teamType);
}
