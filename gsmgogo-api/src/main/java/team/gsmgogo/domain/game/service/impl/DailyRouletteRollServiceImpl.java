package team.gsmgogo.domain.game.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.game.controller.dto.response.DailyRouletteResponse;
import team.gsmgogo.domain.game.entity.GameEntity;
import team.gsmgogo.domain.game.repository.GameJpaRepository;
import team.gsmgogo.domain.game.service.DailyRouletteRollService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class DailyRouletteRollServiceImpl implements DailyRouletteRollService {

    private final UserJpaRepository userJpaRepository;
    private final GameJpaRepository gameJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional
    public DailyRouletteResponse roll() {

        UserEntity currentUser = userFacade.getCurrentUser();

        GameEntity game = gameJpaRepository.findByUser(currentUser)
                .orElse(GameEntity.builder()
                        .dailyRoulette(false)
                        .coinToss(0)
                        .user(currentUser)
                        .build());

        if (game.getDailyRoulette()) {
            throw new ExpectedException("이미 데일리 룰렛을 돌렸습니다.", HttpStatus.NOT_FOUND);
        }

        Random random = new Random();
        int randomNumber = random.nextInt(6) + 1;

        int earnedPoint = 0;

        switch (randomNumber) {
            case 1: {
                earnedPoint = 2000;
                game.rollDailyRoulette();
                break;
            }
            case 2: {
                earnedPoint = 1000;
                game.rollDailyRoulette();
                break;
            }
            case 3: {
                earnedPoint = 500;
                game.rollDailyRoulette();
                break;
            }
            case 4: {
                earnedPoint = 100;
                game.rollDailyRoulette();
                break;
            }
            case 5: {
                earnedPoint = 500;
                break;
            }
            case 6: {
                game.rollDailyRoulette();
                break;
            }
            default: {
                throw new ExpectedException("예상되지 않은 룰렛 항목에 당첨 되었어요.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        currentUser.addPoint(earnedPoint);
        userJpaRepository.save(currentUser);
        gameJpaRepository.save(game);

        return DailyRouletteResponse.builder()
                .earnedPoint(earnedPoint)
                .result(randomNumber)
                .build();

    }
}
