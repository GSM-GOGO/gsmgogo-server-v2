package team.gsmgogo.domain.user.service;

import team.gsmgogo.domain.match.controller.dto.response.MatchResponse;
import team.gsmgogo.domain.user.dto.response.BetMatchResponse;

public interface QueryUserBetMatchService {
    BetMatchResponse execute();
}
