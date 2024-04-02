package team.gsmgogo.domain.team.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.gsmgogo.domain.team.entity.TeamEntity;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

    private final TeamJpaRepository teamJpaRepository;

    @Override
    public Optional<TeamEntity> findById(Long teamId) {
        return teamJpaRepository.findByTeamId(teamId);
    }
}
