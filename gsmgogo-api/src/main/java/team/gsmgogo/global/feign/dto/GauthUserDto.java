package team.gsmgogo.global.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@NoArgsConstructor
public class GauthUserDto {
    private String email;
    private String name;
    private Integer grade;
    private Integer classNum;
    private UserSchoolRole role;
}
