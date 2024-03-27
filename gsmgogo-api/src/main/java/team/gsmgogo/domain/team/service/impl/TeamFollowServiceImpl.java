package team.gsmgogo.domain.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.follow.entity.FollowEntity;
import team.gsmgogo.domain.follow.repository.FollowJpaRepository;
import team.gsmgogo.domain.team.controller.dto.request.TeamBadmintonSaveRequest;
import team.gsmgogo.domain.team.controller.dto.request.TeamFollowRequest;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.team.service.TeamFollowService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class TeamFollowServiceImpl implements TeamFollowService {

    private final FollowJpaRepository followJpaRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional
    public void followTeam(TeamFollowRequest request) {
        UserEntity currentUser = userFacade.getCurrentUser();

        if (followJpaRepository.existsByUser(currentUser))
            throw new ExpectedException("이미 팀을 팔로우 했습니다.", HttpStatus.BAD_REQUEST);

        FollowEntity follow = FollowEntity.builder()
                .team(teamJpaRepository.findByTeamId(request.getTeamId())
                        .orElseThrow(() -> new ExpectedException("팀을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)))
                .user(currentUser)
                .build();

        followJpaRepository.save(follow);
    }
}
