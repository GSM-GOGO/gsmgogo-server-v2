package team.gsmgogo.domain.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.auth.controller.dto.response.ReissueTokenDto;
import team.gsmgogo.domain.auth.entity.RefreshTokenRedisEntity;
import team.gsmgogo.domain.auth.repository.RefreshTokenJpaRepository;
import team.gsmgogo.domain.auth.service.TokenReissueService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.security.jwt.JwtTokenProvider;
import team.gsmgogo.global.security.jwt.dto.TokenResponse;

@Service
@RequiredArgsConstructor
public class TokenReissueServiceImpl implements TokenReissueService {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final JwtTokenProvider tokenProvider;

    @Override
    public ReissueTokenDto execute(String refreshToken) {

        if (refreshToken == null) {
            throw new ExpectedException("리프레시 토큰을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        long userId = Long.parseLong(tokenProvider.getRefreshTokenUserId(refreshToken));
        UserEntity user = userJpaRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        RefreshTokenRedisEntity currentRefreshToken = refreshTokenJpaRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new ExpectedException("리프레시 토큰이 유효하지 않습니다.", HttpStatus.NOT_FOUND));

        TokenResponse newToken = tokenProvider.getToken(userId);

        if (!user.getUserId().equals(currentRefreshToken.getUserId()) || !currentRefreshToken.getRefreshToken().equals(refreshToken)) {
            throw new ExpectedException("리프레시 토큰이 유효하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        currentRefreshToken.updateRefreshToken(newToken.getRefreshToken());
        refreshTokenJpaRepository.save(currentRefreshToken);

        return new ReissueTokenDto(newToken.getAccessToken(), newToken.getRefreshToken());

    }

}
