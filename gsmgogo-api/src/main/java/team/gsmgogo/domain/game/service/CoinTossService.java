package team.gsmgogo.domain.game.service;

import team.gsmgogo.domain.game.controller.dto.request.CoinRequest;
import team.gsmgogo.domain.game.controller.dto.response.CoinResponse;
import team.gsmgogo.domain.game.controller.dto.response.DailyRouletteResponse;

public interface CoinTossService {
    CoinResponse execute(CoinRequest coinRequest);
}
