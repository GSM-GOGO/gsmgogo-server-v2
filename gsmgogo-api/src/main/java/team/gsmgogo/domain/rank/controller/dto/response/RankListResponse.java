package team.gsmgogo.domain.rank.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.user.enums.ClassEnum;
import team.gsmgogo.domain.user.enums.GradeEnum;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RankListResponse {
    private Long userId;
    private String userName;
    private GradeEnum userGrade;
    private ClassEnum userClass;
    private Integer userNum;
    private Integer userPoint;
}
