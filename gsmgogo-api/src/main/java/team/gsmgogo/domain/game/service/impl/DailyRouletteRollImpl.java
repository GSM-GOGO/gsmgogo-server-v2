package team.gsmgogo.domain.game.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.game.controller.dto.response.DailyRouletteResponse;
import team.gsmgogo.domain.game.service.DailyRouletteRoll;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.global.facade.UserFacade;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class DailyRouletteRollImpl implements DailyRouletteRoll {

    private final UserJpaRepository userJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional
    public DailyRouletteResponse roll() {

        Random random = new Random();
        int randomNumber = random.nextInt(6) + 1;

        UserEntity currentUser = userFacade.getCurrentUser();
        int earnedPoint;

        switch (randomNumber) {
            case 1: {
                earnedPoint = 2000;
                break;
            }
            case 2: {
                earnedPoint = 100;
                break;
            }
            case 3: {
                earnedPoint = 500;
                break;
            }
            case 4: {
                earnedPoint = 100;
                break;
            }
            case 5: {
                earnedPoint = 500;
                break;
            }
            case 6: {
                earnedPoint = 2000;
                break;
            }
        }
    }
}
