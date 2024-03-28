package team.gsmgogo.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.user.dto.response.UserInfoResponse;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.domain.user.service.QueryUserInfoService;
import team.gsmgogo.global.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserInfoServiceImpl implements QueryUserInfoService {

    private final UserJpaRepository userJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional(readOnly = true)
    public List<UserInfoResponse> queryUserInfo(String name) {
        UserEntity currentUser = userFacade.getCurrentUser();

        return userJpaRepository.findUserNameAndUserGradeByLimited(name, currentUser.getUserGrade(), 10)
                .stream().map(user -> UserInfoResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .userGrade(user.getUserGrade())
                        .userClass(user.getUserClass())
                        .build()).toList();
    }
}
