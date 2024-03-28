package team.gsmgogo.domain.user.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.user.enums.ClassEnum;
import team.gsmgogo.domain.user.enums.GradeEnum;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoQueryDto {
    private Long userId;
    private String userName;
    private GradeEnum userGrade;
    private ClassEnum userClass;
}
