package team.gsmgogo.domain.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.gsmgogo.global.feign.GauthClient;
import team.gsmgogo.global.feign.dto.GauthTokenDto;
import team.gsmgogo.global.feign.dto.GauthTokenRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GauthClient gauthClient;

    @Value("${gauth.clientId}")
    private String clientId;

    @Value("${gauth.clientSecret}")
    private String clientSecret;

    @Value("${gauth.redirectUrl}")
    private String redirectUri;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
         String loginUrl = "https://gauth.co.kr/login?" +
                    "client_id=" + clientId + "&" +
                    "redirect_uri=" + redirectUri;

         response.sendRedirect(loginUrl);
    }

    @GetMapping("/callback")
    public void callback(@RequestParam("code") String code) throws URISyntaxException {
        GauthTokenDto gauthTokenDto = gauthClient.getToken(new URI("https://server.gauth.co.kr/oauth/token"), GauthTokenRequest.builder()
                        .code(code)
                        .clientId("83826b547e88434eb1a6dfc27c2ba7f9d877615fc09a438c8703fbbea15a202e")
                        .clientSecret("9e01491916754f078ddd54c37b6e2ceca5567d5e961c41d8915fb67aae90e9b8")
                        .redirectUri("http://localhost:8080/auth/callback")
                .build());
    }

}
