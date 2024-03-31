package team.gsmgogo.domain.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.follow.repository.FollowJpaRepository;
import team.gsmgogo.domain.normalteamparticipate.repository.NormalTeamParticipateJpaRepository;
import team.gsmgogo.domain.team.controller.dto.response.TeamClassType;
import team.gsmgogo.domain.team.controller.dto.response.TeamListResponse;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.team.service.TeamGetService;
import team.gsmgogo.domain.teamparticipate.repository.TeamParticipateJpaRepository;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.enums.ClassEnum;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamGetServiceImpl implements TeamGetService {
    private final TeamJpaRepository teamJpaRepository;
    private final TeamParticipateJpaRepository teamParticipateJpaRepository;
    private final NormalTeamParticipateJpaRepository normalTeamParticipateJpaRepository;
    private final FollowJpaRepository followJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional(readOnly = true)
    public List<TeamListResponse> getTeam(String type) {
        if (!isValidTeamType(type)) throw new ExpectedException("검색 조건이 잘못되었습니다.", HttpStatus.BAD_REQUEST);

        UserEntity user = userFacade.getCurrentUser();
        List<TeamEntity> teamEntityList = teamJpaRepository.findByTeamType(TeamType.valueOf(type));

        return teamEntityList.stream().map(teamEntity -> new TeamListResponse(
                teamEntity.getTeamId(),
                teamEntity.getTeamName(),
                teamEntity.getTeamGrade(),
                toTeamClassType(teamEntity.getTeamClass()),
                teamEntity.getWinCount(),
                followJpaRepository.existsByUserAndTeam(user, teamEntity),
                teamEntity.getTeamType().equals(TeamType.NORMAL)
                        ? normalTeamParticipateJpaRepository.existsByUserAndTeam(user, teamEntity)
                        : teamParticipateJpaRepository.existsByUserAndTeam(user, teamEntity),
                teamEntity.getBadmintonRank()
            )).toList();
    }

    private TeamClassType toTeamClassType(ClassEnum classEnum) {
        if (classEnum == ClassEnum.ONE || classEnum == ClassEnum.TWO)
            return TeamClassType.SW;
        else
            return TeamClassType.EB;
    }

    private boolean isValidTeamType(String type) {
        for (TeamType teamType : TeamType.values()) {
            if (teamType.name().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
