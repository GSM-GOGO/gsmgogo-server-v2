package team.gsmgogo.domain.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.team.controller.dto.request.TeamDeleteRequest;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.team.service.TeamDeleteService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class TeamDeleteServiceImpl implements TeamDeleteService {

    private final TeamJpaRepository teamJpaRepository;
    private final UserFacade userFacade;

    @Override
    public void deleteTeam(TeamDeleteRequest request) {
        TeamEntity team = teamJpaRepository.findByTeamId(request.getTeamId())
                .orElseThrow(() -> new ExpectedException("팀을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        UserEntity currentUser = userFacade.getCurrentUser();

        if (team.getTeamGrade() != currentUser.getUserGrade() || team.getTeamClass() != currentUser.getUserClass()) {
            throw new ExpectedException("팀을 동록한 사람이 아닙니다.", HttpStatus.BAD_REQUEST);
        }

        teamJpaRepository.delete(team);
    }
}
