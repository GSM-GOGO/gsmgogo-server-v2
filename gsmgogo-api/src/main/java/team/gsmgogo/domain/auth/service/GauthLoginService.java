package team.gsmgogo.domain.auth.service;

import team.gsmgogo.domain.auth.controller.dto.response.TokenDto;
import team.gsmgogo.global.feign.dto.GauthTokenDto;

public interface GauthLoginService {
    TokenDto execute(String code);
}
