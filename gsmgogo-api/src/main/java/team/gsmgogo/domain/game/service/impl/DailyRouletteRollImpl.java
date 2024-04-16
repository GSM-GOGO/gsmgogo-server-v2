package team.gsmgogo.domain.game.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.game.controller.dto.response.DailyRouletteResponse;
import team.gsmgogo.domain.game.service.DailyRouletteRoll;

@Service
@RequiredArgsConstructor
public class DailyRouletteRollImpl implements DailyRouletteRoll {


    @Override
    @Transactional
    public DailyRouletteResponse roll() {

        return null;

    }
}
