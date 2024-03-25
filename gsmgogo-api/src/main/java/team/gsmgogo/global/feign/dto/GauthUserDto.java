package team.gsmgogo.global.feign.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GauthUserDto {
    private String email;
    private String name;
    private Integer grade;
    private Integer classNum;
    private Integer num;
    @Enumerated(EnumType.STRING)
    private UserSchoolRole role;
}
