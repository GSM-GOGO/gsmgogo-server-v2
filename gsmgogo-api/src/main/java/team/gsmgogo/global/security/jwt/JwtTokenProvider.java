package team.gsmgogo.global.security.jwt;

import io.jsonwebtoken.security.Keys;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.manager.CookieManager;
import team.gsmgogo.global.security.jwt.dto.TokenResponse;
import team.gsmgogo.global.security.principle.AuthDetailsService;
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

import java.nio.charset.StandardCharsets;
import java.security.Key;
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

    public static final String ACCESS_KEY = "accessToken";
    public static final String REFRESH_KEY = "refreshToken";

    private Key getSignInKey(String secretKey){
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }

    public TokenResponse getToken(Long userId) {
        String accessToken = generateAccessToken(userId, accessExp);
        String refreshToken = generateRefrshToken(userId, refreshExp);

        return new TokenResponse(accessToken, refreshToken);
    }

    private String generateAccessToken(Long userId, long expiration) {
        final Claims claims = Jwts.claims();
        claims.put("userId", Long.toString(userId));

        return Jwts.builder().signWith(SignatureAlgorithm.HS256, secretKey)
                .setClaims(claims)
                .setHeaderParam("typ", ACCESS_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    private String generateRefrshToken(Long userId, long expiration) {
        final Claims claims = Jwts.claims();
        claims.put("userId", Long.toString(userId));

        return Jwts.builder().signWith(SignatureAlgorithm.HS256, refreshKey)
                .setClaims(claims)
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

    public String getRefreshTokenUserId(String token){
        return getTokenBody(token, refreshKey).get("userId", String.class);
    }

    public UsernamePasswordAuthenticationToken authorization(String token) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getTokenSubject(String subject) {
        return getTokenBody(subject, secretKey).getSubject();
    }

    private Claims getTokenBody(String token, String secret) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new ExpectedException("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }
    }
}
