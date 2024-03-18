package team.gsmgogo.domain.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.auth.service.GauthLoginService;
import team.gsmgogo.global.feign.GauthClient;
import team.gsmgogo.global.feign.dto.GauthTokenDto;
import team.gsmgogo.global.feign.dto.GauthTokenRequest;
import team.gsmgogo.global.feign.dto.GauthUserDto;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class GauthLoginServiceImpl implements GauthLoginService {

    private final GauthClient gauthClient;

    @Value("${gauth.clientId}")
    private String clientId;

    @Value("${gauth.clientSecret}")
    private String clientSecret;

    @Value("${gauth.redirectUrl}")
    private String redirectUri;

    @Override
    @Transactional
    public void execute(String code) throws URISyntaxException {

        GauthTokenDto gauthTokenDto = gauthClient.getToken(new URI("https://server.gauth.co.kr/oauth/token"), GauthTokenRequest.builder()
                .code(code)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .build());


        GauthUserDto gauthUserDto = gauthClient.getInfo(new URI("https://open.gauth.co.kr/user"), "Bearer " + gauthTokenDto.getAccessToken());

    }

}
