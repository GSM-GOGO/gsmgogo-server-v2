package team.gsmgogo.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.user.dto.response.UserInfoResponse;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.enums.GradeEnum;
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
    public List<UserInfoResponse> queryUserInfo(String name, String type) {
        UserEntity currentUser = userFacade.getCurrentUser();

        List<GradeEnum> grades;
        if(type != null & TeamType.valueOf(type).equals(TeamType.BADMINTON)) {
            boolean isGradeOne = currentUser.getUserGrade() == GradeEnum.ONE;
            grades = isGradeOne ? List.of(GradeEnum.ONE) : List.of(GradeEnum.TWO, GradeEnum.THREE);
        } else grades = List.of(currentUser.getUserGrade());

        return userJpaRepository.findTop5ByUserNameContainingAndUserGradeInOrderByUserNameAsc(name, grades)
                .stream().map(user -> UserInfoResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .userGrade(user.getUserGrade())
                        .userClass(user.getUserClass())
                        .build()).toList();
    }
}
