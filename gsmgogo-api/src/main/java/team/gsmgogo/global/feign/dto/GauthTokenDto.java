package team.gsmgogo.global.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GauthTokenDto {
    private String accessToken;
    private String refreshToken;
}
