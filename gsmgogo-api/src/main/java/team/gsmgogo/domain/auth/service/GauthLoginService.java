package team.gsmgogo.domain.auth.service;

import team.gsmgogo.global.feign.dto.GauthTokenDto;

public interface GauthLoginService {
    GauthTokenDto execute(String code);
}
