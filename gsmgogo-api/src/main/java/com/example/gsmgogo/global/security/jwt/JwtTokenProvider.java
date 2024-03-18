package com.example.gsmgogo.global.security.jwt;

import com.example.gsmgogo.global.exception.error.ExpectedException;
import com.example.gsmgogo.global.manager.CookieManager;
import com.example.gsmgogo.global.security.jwt.dto.TokenResponse;
import com.example.gsmgogo.global.security.principle.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final AuthDetailsService authDetailsService;
    private final CookieManager cookieManager;

    @Value("${spring.jwt.secretKey}")
    private String secretKey;
    @Value("${spring.jwt.refreshKey}")
    private String refreshKey;
    @Value("${spring.jwt.accessExp}")
    public Long accessExp;
    @Value("${spring.jwt.refreshExp}")
    public Long refreshExp;

    private static final String ACCESS_KEY = "access_token";
    private static final String REFRESH_KEY = "refresh_token";

    public TokenResponse getToken(String email) {
        String accessToken = generateAccessToken(email, accessExp);
        String refreshToken = generateRefrshToken(email, refreshExp);

        return new TokenResponse(accessToken, refreshToken);
    }

    private String generateAccessToken(String email, long expiration) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, secretKey)
                .setSubject(email)
                .setHeaderParam("typ", ACCESS_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    private String generateRefrshToken(String email, long expiration) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, refreshKey)
                .setSubject(email)
                .setHeaderParam("typ", REFRESH_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        return cookieManager.getCookieValue(request, ACCESS_KEY);
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return cookieManager.getCookieValue(request, REFRESH_KEY);
    }

    public UsernamePasswordAuthenticationToken authorization(String token) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getTokenSubject(String subject) {
        return getTokenBody(subject).getSubject();
    }

    private Claims getTokenBody(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new ExpectedException("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }
    }
}
