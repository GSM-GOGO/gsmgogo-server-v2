package team.gsmgogo.domain.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.team.controller.dto.response.FormationParticipateDto;
import team.gsmgogo.domain.team.controller.dto.response.TeamClassType;
import team.gsmgogo.domain.team.controller.dto.response.TeamFormationResponse;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.repository.TeamRepository;
import team.gsmgogo.domain.team.service.TeamFormationGetService;
import team.gsmgogo.domain.teamparticipate.entity.TeamParticipateEntity;
import team.gsmgogo.domain.teamparticipate.repository.TeamParticipateJpaRepository;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.enums.ClassEnum;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamFormationGetServiceImpl implements TeamFormationGetService {
    private final TeamRepository teamRepository;
    private final TeamParticipateJpaRepository teamParticipateJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional(readOnly = true)
    public TeamFormationResponse execute(String teamId) {
        UserEntity currentUser = userFacade.getCurrentUser();

        TeamEntity team = teamRepository.findById(Long.valueOf(teamId))
            .orElseThrow(() -> new ExpectedException("해당 팀을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        return new TeamFormationResponse(
            team.getTeamId(),
            team.getTeamName(),
            team.getTeamType(),
            team.getTeamGrade(),
            toTeamClassType(team.getTeamClass()),
            team.getAuthor().getUserId().equals(currentUser.getUserId()),
            team.getWinCount(),
                team.getTeamParticipates().stream().map(teamParticipate -> new FormationParticipateDto(
                teamParticipate.getUser().getUserId(),
                teamParticipate.getUser().getUserName(),
                teamParticipate.getPositionX(),
                teamParticipate.getPositionY()
            )).toList(),
            team.getBadmintonRank()
        );
    }

    private TeamClassType toTeamClassType(ClassEnum classEnum) {
        if (classEnum == ClassEnum.ONE || classEnum == ClassEnum.TWO)
            return TeamClassType.SW;
        else
            return TeamClassType.EB;
    }
}
