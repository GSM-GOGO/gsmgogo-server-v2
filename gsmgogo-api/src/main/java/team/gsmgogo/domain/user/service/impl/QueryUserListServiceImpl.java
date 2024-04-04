package team.gsmgogo.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.user.dto.response.UserInfoResponse;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.enums.ClassEnum;
import team.gsmgogo.domain.user.enums.GradeEnum;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.domain.user.service.QueryUserListService;
import team.gsmgogo.global.facade.UserFacade;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserListServiceImpl implements QueryUserListService {

    private final UserJpaRepository userJpaRepository;
    private final UserFacade userFacade;

    @Override
    public List<UserInfoResponse> queryUserList() {
        UserEntity currentUser = userFacade.getCurrentUser();

        List<ClassEnum> userClasses = new ArrayList<>();
        userClasses.add(currentUser.getUserClass());
        userClasses.add(userOtherMajorClass(currentUser.getUserClass()));

        List<GradeEnum> grades = List.of(currentUser.getUserGrade());
        List<UserEntity> userList = userJpaRepository.findAllByUserGradeInAndUserClassIn(grades, userClasses);

        return userList.stream().map(user -> UserInfoResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userGrade(user.getUserGrade())
                .userClass(user.getUserClass()).build()
        ).toList();
    }

    private ClassEnum userOtherMajorClass(ClassEnum userClass) {
        if (userClass.equals(ClassEnum.ONE))
            return ClassEnum.TWO;
        else if (userClass.equals(ClassEnum.TWO))
            return ClassEnum.ONE;
        else if (userClass.equals(ClassEnum.THREE))
            return ClassEnum.FOUR;
        else return ClassEnum.THREE;
    }
}
