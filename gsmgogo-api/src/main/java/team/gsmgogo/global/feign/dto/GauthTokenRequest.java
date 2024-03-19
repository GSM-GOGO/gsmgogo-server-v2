package team.gsmgogo.global.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GauthTokenRequest {
    private String code;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
