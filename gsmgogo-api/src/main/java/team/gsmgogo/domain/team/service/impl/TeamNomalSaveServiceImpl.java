package team.gsmgogo.domain.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.team.controller.dto.request.TeamNomalSaveRequest;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.team.service.TeamNomalSaveService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamNomalSaveServiceImpl implements TeamNomalSaveService {

    private TeamJpaRepository teamJpaRepository;
    private UserFacade userFacade;

    @Override
    public void saveNomalTeam(List<TeamNomalSaveRequest> request) {
        UserEntity currentUser = userFacade.getCurrentUser();

        if (teamJpaRepository.existsByTeamGradeAndTeamClassAndTeamType
                (currentUser.getUserGrade(), currentUser.getUserClass(), request.getTeamType()))
            throw new ExpectedException("이미 등록된 팀이 있습니다.", HttpStatus.BAD_REQUEST);
    }
}
