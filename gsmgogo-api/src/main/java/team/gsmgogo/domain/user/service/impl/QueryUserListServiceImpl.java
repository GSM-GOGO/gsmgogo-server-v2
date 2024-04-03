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
    public List<UserInfoResponse> queryUserList(String type) {
        type = null;

        UserEntity currentUser = userFacade.getCurrentUser();

        List<ClassEnum> userClasses = new ArrayList<>();
        userClasses.add(currentUser.getUserClass());
        userClasses.add(userOtherMajorClass(currentUser.getUserClass()));

        List<UserEntity> userList;
        List<GradeEnum> grades;

        TeamType teamType = TeamType.valueOf(type);

        if(teamType == TeamType.BADMINTON){
            boolean isGradeOne = currentUser.getUserGrade() == GradeEnum.ONE;
            grades = isGradeOne ? List.of(GradeEnum.ONE) : List.of(GradeEnum.TWO, GradeEnum.THREE);
        } else grades = List.of(currentUser.getUserGrade());

        userList = userJpaRepository.findAllByUserGradeInAndUserClassIn(grades, userClasses);

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
