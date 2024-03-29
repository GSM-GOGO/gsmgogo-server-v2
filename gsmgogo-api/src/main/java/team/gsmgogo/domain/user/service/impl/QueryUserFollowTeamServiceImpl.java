package team.gsmgogo.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.follow.entity.FollowEntity;
import team.gsmgogo.domain.follow.repository.FollowJpaRepository;
import team.gsmgogo.domain.user.dto.response.UserFollowTeamIdResponse;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.service.QueryUserFollowTeamService;
import team.gsmgogo.global.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class QueryUserFollowTeamServiceImpl implements QueryUserFollowTeamService {

    private final UserFacade userFacade;
    private final FollowJpaRepository followJpaRepository;

    @Override
    public UserFollowTeamIdResponse queryUserFollowTeam() {
        UserEntity currentUser = userFacade.getCurrentUser();
        FollowEntity follow = followJpaRepository.findByUser(currentUser).orElse(null);

        return new UserFollowTeamIdResponse(follow != null ? follow.getFollowId() : null);
    }
}
