package team.gsmgogo.domain.team.repository;

import team.gsmgogo.domain.team.entity.TeamEntity;
import java.util.Optional;

public interface TeamRepository {

    Optional<TeamEntity> findById(Long teamId);

}
