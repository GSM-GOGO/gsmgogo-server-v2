package team.gsmgogo.global.feign.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.user.enums.Gender;

@Getter
@NoArgsConstructor
public class GauthUserDto {
    private String email;
    private String name;
    private Integer grade;
    private Integer classNum;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Integer num;
    @Enumerated(EnumType.STRING)
    private UserSchoolRole role;
}
