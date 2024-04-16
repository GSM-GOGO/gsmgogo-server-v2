package team.gsmgogo.domain.match.service;

import team.gsmgogo.domain.match.controller.dto.response.MatchResponse;

public interface MatchService {
    MatchResponse execute(int month, int day);
}
