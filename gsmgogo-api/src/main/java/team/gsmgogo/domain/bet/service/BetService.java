package team.gsmgogo.domain.bet.service;

import team.gsmgogo.domain.bet.controller.dto.BetRequest;

public interface BetService {
    void execute(BetRequest betRequest);
}
