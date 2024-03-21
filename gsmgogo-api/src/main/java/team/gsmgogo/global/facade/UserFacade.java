package team.gsmgogo.global.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.global.exception.error.ExpectedException;

@Configuration
@RequiredArgsConstructor
public class UserFacade {
    private final UserJpaRepository userJpaRepository;

    public UserEntity getCurrentUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(userId);
    }

    public UserEntity getUserByEmail(String userId) {
        return userJpaRepository.findByUserId(Long.valueOf(userId))
                .orElseThrow(() -> new ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

}
