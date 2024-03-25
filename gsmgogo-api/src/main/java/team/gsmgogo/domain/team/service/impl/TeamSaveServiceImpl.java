package team.gsmgogo.domain.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.team.controller.dto.request.TeamSaveRequest;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.team.service.TeamSaveService;
import team.gsmgogo.domain.teamparticipate.entity.TeamParticipateEntity;
import team.gsmgogo.domain.teamparticipate.repository.TeamParticipateJpaRepository;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamSaveServiceImpl implements TeamSaveService {

    private final TeamJpaRepository teamJpaRepository;
    private final TeamParticipateJpaRepository teamParticipateJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional
    public void saveTeam(TeamSaveRequest request) {
        UserEntity currentUser = userFacade.getCurrentUser();

        if (teamJpaRepository.existsByTeamGradeAndTeamClassAndTeamType
                (currentUser.getUserGrade(), currentUser.getUserClass(), request.getTeamType()))
            throw new ExpectedException("이미 등록된 팀이 있습니다.", HttpStatus.BAD_REQUEST);

        TeamEntity newTeam = TeamEntity.builder()
                .teamName(request.getTeamName())
                .winCount(0)
                .isSurvived(true)
                .teamClass(currentUser.getUserClass())
                .teamGrade(currentUser.getUserGrade())
                .teamType(request.getTeamType())
                .build();

        TeamEntity savedTeam = teamJpaRepository.save(newTeam);

        List<TeamParticipateEntity> participates = request.getParticipates()
                .stream().map(user -> TeamParticipateEntity.builder()
                        .team(savedTeam)
                        .user(userJpaRepository.findByUserId(user.getUserId())
                                .orElseThrow(() -> new ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)))
                        .positionX(user.getPositionX())
                        .positionY(user.getPositionY())
                        .build()
                ).toList();

        teamParticipateJpaRepository.saveAll(participates);

    }
}
