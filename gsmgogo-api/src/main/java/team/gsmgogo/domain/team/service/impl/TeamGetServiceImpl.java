package team.gsmgogo.domain.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.follow.repository.FollowJpaRepository;
import team.gsmgogo.domain.team.controller.dto.response.TeamListResponse;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.team.service.TeamGetService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamGetServiceImpl implements TeamGetService {
    private final TeamJpaRepository teamJpaRepository;
    private final FollowJpaRepository followJpaRepository;
    private final UserFacade userFacade;

    @Override
    public List<TeamListResponse> getTeam(TeamType teamType) {
        UserEntity user = userFacade.getCurrentUser();
        List<TeamEntity> teamEntityList = teamJpaRepository.findByTeamType(teamType);

        return teamEntityList.stream().map(teamEntity -> new TeamListResponse(
                teamEntity.getTeamId(),
                teamEntity.getTeamName(),
                teamEntity.getTeamGrade().getRole(),
                teamEntity.getTeamClass().getRole(),
                teamEntity.getWinCount(),
                followJpaRepository.existsByUserAndTeam(user, teamEntity),
                teamEntity.getBadmintonRank().name()
            )).toList();
    }
}
