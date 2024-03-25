package team.gsmgogo.domain.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.nomalteamparticipate.entity.NomalTeamParticipateEntity;
import team.gsmgogo.domain.nomalteamparticipate.repository.NomalTeamParticipateJpaRepository;
import team.gsmgogo.domain.team.controller.dto.request.TeamNomalSaveRequest;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.team.service.TeamNomalSaveService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamNomalSaveServiceImpl implements TeamNomalSaveService {

    private final TeamJpaRepository teamJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final NomalTeamParticipateJpaRepository nomalTeamParticipateJpaRepository;
    private final UserFacade userFacade;

    @Override
    public void saveNomalTeam(List<TeamNomalSaveRequest> request) {
        UserEntity currentUser = userFacade.getCurrentUser();

        if (teamJpaRepository.existsByTeamGradeAndTeamClassAndTeamType
                (currentUser.getUserGrade(), currentUser.getUserClass(), TeamType.NOMAL))
            throw new ExpectedException("이미 등록된 팀이 있습니다.", HttpStatus.BAD_REQUEST);

        TeamEntity newTeam = TeamEntity.builder()
                .teamClass(currentUser.getUserClass())
                .teamGrade(currentUser.getUserGrade())
                .teamType(TeamType.NOMAL)
                .build();

        TeamEntity savedTeam = teamJpaRepository.save(newTeam);

        List<NomalTeamParticipateEntity> nomalTeamParticipateEntities = request.stream().flatMap(
                participate -> participate.getTeamTypes().stream().map(team ->
                        NomalTeamParticipateEntity.builder()
                                .team(savedTeam)
                                .user(userJpaRepository.findByUserId(participate.getUserId())
                                        .orElseThrow(() -> new ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)))
                                .nomalTeamType(team).build())).toList();

        nomalTeamParticipateJpaRepository.saveAll(nomalTeamParticipateEntities);
    }
}

