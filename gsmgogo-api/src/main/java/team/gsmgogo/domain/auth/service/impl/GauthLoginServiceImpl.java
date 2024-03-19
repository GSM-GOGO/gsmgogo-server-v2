package team.gsmgogo.domain.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.auth.controller.dto.response.TokenDto;
import team.gsmgogo.domain.auth.entity.RefreshTokenRedisEntity;
import team.gsmgogo.domain.auth.repository.RefreshTokenJpaRepository;
import team.gsmgogo.domain.auth.service.GauthLoginService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.enums.ClassEnum;
import team.gsmgogo.domain.user.enums.GradeEnum;
import team.gsmgogo.domain.user.enums.Role;
import team.gsmgogo.domain.user.enums.SchoolRole;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.feign.GauthClient;
import team.gsmgogo.global.feign.dto.GauthTokenDto;
import team.gsmgogo.global.feign.dto.GauthTokenRequest;
import team.gsmgogo.global.feign.dto.GauthUserDto;
import team.gsmgogo.global.feign.dto.UserSchoolRole;
import team.gsmgogo.global.security.jwt.JwtTokenProvider;
import team.gsmgogo.global.security.jwt.dto.TokenResponse;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class GauthLoginServiceImpl implements GauthLoginService {

    private final GauthClient gauthClient;
    private final UserJpaRepository userJpaRepository;
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Value("${spring.jwt.refreshExp}")
    private Long refreshExp;
    @Value("${gauth.clientId}")
    private String clientId;
    @Value("${gauth.clientSecret}")
    private String clientSecret;
    @Value("${gauth.redirectUrl}")
    private String redirectUri;
    @Value("${gauth.authUrl}")
    public String authUrl;
    @Value("${gauth.userApiUrl}")
    public String userApiUrl;

    @Override
    @Transactional
    public TokenDto execute(String code) {

        try {
            GauthTokenDto gauthTokenDto = gauthClient.getToken(new URI(authUrl), GauthTokenRequest.builder()
                    .code(code)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .redirectUri(redirectUri)
                    .build());

            GauthUserDto gauthUserDto = gauthClient.getInfo(new URI(userApiUrl), "Bearer " + gauthTokenDto.getAccessToken());

            Integer grade = gauthUserDto.getGrade();
            Integer classNum = gauthUserDto.getClassNum();
            UserSchoolRole userSchoolRole = gauthUserDto.getRole();
            String name = gauthUserDto.getName();
            String email = gauthUserDto.getEmail();

            UserEntity currentUser = userJpaRepository.findByUserEmail(gauthUserDto.getEmail()).orElse(null);

            boolean isSignedUp = true;

            if (currentUser == null) {
                UserEntity newUser = UserEntity.builder()
                        .userName(name)
                        .userEmail(email)
                        .userGrade(
                            switch (grade) {
                                case 1 -> GradeEnum.ONE;
                                case 2 -> GradeEnum.TWO;
                                case 3 -> GradeEnum.THREE;
                                default -> throw new ExpectedException("유저 엔티티 저장을 위한 매핑중 발생한 오류입니다", HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                        )
                        .userClass(
                            switch (classNum) {
                                case 1 -> ClassEnum.ONE;
                                case 2 -> ClassEnum.TWO;
                                case 3 -> ClassEnum.THREE;
                                case 4 -> ClassEnum.FOUR;
                                default -> throw new ExpectedException("유저 엔티티 저장을 위한 매핑중 발생한 오류입니다", HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                        )
                        .schoolRole(
                                switch (userSchoolRole) {
                                    case ROLE_STUDENT -> SchoolRole.STUDENT;
                                    case ROLE_TEACHER -> SchoolRole.TEACHER;
                                }

                        )
                        .role(Role.USER)
                        .point(30000).build();

                userJpaRepository.save(newUser);

                isSignedUp = false;
            } else if (currentUser.getPhoneNumber().isEmpty()) {
                isSignedUp = false;
            }

            TokenResponse token = tokenProvider.getToken(email);

            RefreshTokenRedisEntity refreshToken = RefreshTokenRedisEntity.builder()
                    .userEmail(email)
                    .refreshToken(token.getRefreshToken())
                    .expiredAt(refreshExp).build();

            String savedRefreshToken = refreshTokenJpaRepository.save(refreshToken).getRefreshToken();

            return new TokenDto(token.getAccessToken(), savedRefreshToken, isSignedUp);

        } catch (Exception e) {
            throw new ExpectedException("Gauth 외부 API 호출중 에러가 발생했어요!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
