package team.gsmgogo.domain.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.auth.entity.BlackListRedisEntity;
import team.gsmgogo.domain.auth.entity.RefreshTokenRedisEntity;
import team.gsmgogo.domain.auth.repository.BlackListJpaRepository;
import team.gsmgogo.domain.auth.repository.RefreshTokenJpaRepository;
import team.gsmgogo.domain.auth.service.LogoutService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final BlackListJpaRepository blackListJpaRepository;
    private final RedisTemplate redisTemplate;
    private final UserFacade userFacade;

    @Override
    @Transactional
    public void logout(String accessToken) {
        UserEntity currentUser = userFacade.getCurrentUser();
        RefreshTokenRedisEntity refreshToken = refreshTokenJpaRepository.findByUserId(currentUser.getUserId())
                .orElseThrow(() -> new ExpectedException("리프레시 토큰을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
        refreshTokenJpaRepository.delete(refreshToken);

        if (redisTemplate.opsForValue().get(accessToken) != null) {
            throw new ExpectedException("이미 블랙리스트에 있습니다.", HttpStatus.BAD_REQUEST);
        }

        BlackListRedisEntity blackList = BlackListRedisEntity.builder()
                .userId(currentUser.getUserId())
                .accessToken(accessToken)
                .timeToLive(7200L)
                .build();

        blackListJpaRepository.save(blackList);
    }
}
