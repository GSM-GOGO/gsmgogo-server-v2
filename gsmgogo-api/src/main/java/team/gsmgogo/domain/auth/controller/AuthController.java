package team.gsmgogo.domain.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${gauth.clientId}")
    private String clientId;

    @Value("${gauth.redirectUrl}")
    private String redirectUri;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
         String loginUrl = "https://gauth.co.kr/login?" +
                    "client_id=" + clientId + "&" +
                    "redirect_uri=" + redirectUri;

         response.sendRedirect(loginUrl);
    }

}
