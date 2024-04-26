package team.gsmgogo.global.principle;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final UserJpaRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserId(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return new AuthDetails(user);
    }
}
