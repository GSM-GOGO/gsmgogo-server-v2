package team.gsmgogo.domain.game.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.game.controller.dto.request.CoinRequest;
import team.gsmgogo.domain.game.controller.dto.response.CoinResponse;
import team.gsmgogo.domain.game.entity.GameEntity;
import team.gsmgogo.domain.game.enums.CoinStatus;
import team.gsmgogo.domain.game.repository.GameJpaRepository;
import team.gsmgogo.domain.game.service.CoinTossService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class CoinTossServiceImpl implements CoinTossService {
    private final GameJpaRepository gameJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional
    public CoinResponse execute(CoinRequest coinRequest) {
        UserEntity currentUser = userFacade.getCurrentUser();
        GameEntity game = gameJpaRepository.findByUser(currentUser)
            .orElse(GameEntity.builder()
                .dailyRoulette(false)
                .coinToss(0)
                .user(currentUser)
                .build()
            );

        if(game.getCoinToss() >= 10){
            throw new ExpectedException("동전 던지기는 하루에 10번만 할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }

        if(coinRequest.getPoint() > 3000){
            throw new ExpectedException("최대 3000 포인트까지만 배팅할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }

        SecureRandom secureRandom = new SecureRandom();
        CoinStatus result = secureRandom.nextBoolean() ? CoinStatus.TAIL : CoinStatus.HEAD;

        boolean isWin = result == coinRequest.getPrediction();
        Integer earnedPoint = isWin ? coinRequest.getPoint() * 2 : null;

        game.addCoinTossCount();
        gameJpaRepository.save(game);

        return new CoinResponse(
            coinRequest.getPrediction(),
            result,
            isWin,
            earnedPoint
        );
    }
}
