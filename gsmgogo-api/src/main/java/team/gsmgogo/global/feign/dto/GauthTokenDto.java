package team.gsmgogo.global.feign.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GauthTokenDto {
    private String accessToken;
    private String refreshToken;
}
