package team.gsmgogo.domain.auth.service;

import team.gsmgogo.domain.auth.controller.dto.response.ReissueTokenDto;

public interface TokenReissueService {
    ReissueTokenDto execute(String refreshToken);
}
