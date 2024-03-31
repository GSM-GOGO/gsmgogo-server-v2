package team.gsmgogo.global.security.jwt;

import io.jsonwebtoken.*;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.exception.error.TokenException;
import team.gsmgogo.global.security.jwt.dto.TokenResponse;
import team.gsmgogo.global.security.principle.AuthDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final AuthDetailsService authDetailsService;

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

    public TokenResponse getToken(Long userId) {
        String accessToken = generateAccessToken(userId, accessExp);
        String refreshToken = generateRefrshToken(userId, refreshExp);

        return new TokenResponse(accessToken, refreshToken);
    }

    private String generateAccessToken(Long userId, long expiration) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, secretKey)
                .setSubject(String.valueOf(userId))
                .setHeaderParam("typ", ACCESS_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    private String generateRefrshToken(Long userId, long expiration) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, refreshKey)
                .setSubject(String.valueOf(userId))
                .setHeaderParam("typ", REFRESH_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public String getRefreshTokenUserId(String token){
        return getTokenBody(token, refreshKey).get("sub", String.class);
    }

    public UsernamePasswordAuthenticationToken authorization(String token) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String getTokenSubject(String subject) {
        return getTokenBody(subject, secretKey).getSubject();
    }

    private Claims getTokenBody(String token, String secret) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenException("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            throw new TokenException("검증되지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new TokenException("토큰 예외입니다.", HttpStatus.FORBIDDEN);
        }
    }
}
