package team.gsmgogo.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.gsmgogo.domain.auth.controller.dto.request.AuthSendCodeRequest;
import team.gsmgogo.domain.auth.controller.dto.response.AuthCallBackCodeResponse;
import team.gsmgogo.domain.auth.controller.dto.response.AuthLoginUrlResponse;
import team.gsmgogo.domain.auth.controller.dto.response.ReissueTokenDto;
import team.gsmgogo.domain.auth.controller.dto.response.TokenDto;
import team.gsmgogo.domain.auth.service.CheckVerifyCodeService;
import team.gsmgogo.domain.auth.service.GauthLoginService;
import team.gsmgogo.domain.auth.service.MessageSendService;
import team.gsmgogo.domain.auth.service.TokenReissueService;
import team.gsmgogo.global.manager.CookieManager;
import team.gsmgogo.global.security.jwt.TokenProvider;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MessageSendService messageSendService;
    private final CheckVerifyCodeService checkVerifyCodeService;
    private final GauthLoginService gauthLoginService;
    private final TokenReissueService tokenReissueService;
    private final CookieManager cookieManager;

    @Value("${gauth.clientId}")
    private String clientId;
    @Value("${gauth.redirectUrl}")
    private String redirectUri;
    @Value("${spring.jwt.accessExp}")
    public Long accessExp;
    @Value("${spring.jwt.refreshExp}")
    public Long refreshExp;

    @GetMapping("/login")
    public ResponseEntity<AuthLoginUrlResponse> login(HttpServletResponse response) throws IOException {
        String loginUrl = "https://gauth.co.kr/login?" +
                "client_id=" + clientId + "&" +
                "redirect_uri=" + redirectUri;

        return ResponseEntity.ok(new AuthLoginUrlResponse(loginUrl));
    }

    @GetMapping("/callback")
    public ResponseEntity<AuthCallBackCodeResponse> callback(@RequestParam("code") String code, HttpServletResponse response) {
        TokenDto tokenDto = gauthLoginService.execute(code);
        cookieManager.addTokenCookie(response, TokenProvider.ACCESS_KEY, tokenDto.getAccessToken(), accessExp, true);
        cookieManager.addTokenCookie(response, TokenProvider.REFRESH_KEY, tokenDto.getRefreshToken(), refreshExp, true);
        return ResponseEntity.ok(new AuthCallBackCodeResponse(tokenDto.getIsSignup()));
    }

    @GetMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieManager.getCookieValue(request, TokenProvider.REFRESH_KEY);
        ReissueTokenDto reissueTokenDto = tokenReissueService.execute(refreshToken);
        cookieManager.addTokenCookie(response, TokenProvider.ACCESS_KEY, reissueTokenDto.getAccessToken(), accessExp, true);
        cookieManager.addTokenCookie(response, TokenProvider.REFRESH_KEY, reissueTokenDto.getRefreshToken(), refreshExp, true);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sms")
    public ResponseEntity<Void> sendCodeMessage(@RequestBody AuthSendCodeRequest request){
        messageSendService.execute(request.getPhoneNumber());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sms/test")
    public ResponseEntity<String> sendCodeMessageTest(@RequestBody AuthSendCodeRequest request){
        return ResponseEntity.ok(messageSendService.test(request.getPhoneNumber()));
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> checkVerifyCode(@RequestParam("code") String verifyCode){
        checkVerifyCodeService.execute(verifyCode);
        return ResponseEntity.ok().build();
    }
}