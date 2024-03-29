package team.gsmgogo.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.user.dto.response.UserInfoResponse;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.domain.user.service.QueryUserListService;
import team.gsmgogo.global.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserListServiceImpl implements QueryUserListService {

    private final UserJpaRepository userJpaRepository;
    private final UserFacade userFacade;

    @Override
    public List<UserInfoResponse> queryUserList() {
        UserEntity currentUser = userFacade.getCurrentUser();

        return userJpaRepository
                .findAllByUserGradeAndUserClass(currentUser.getUserGrade(), currentUser.getUserClass())
                .stream().map(user -> UserInfoResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .userGrade(user.getUserGrade())
                        .userClass(user.getUserClass()).build()).toList();
    }
}
