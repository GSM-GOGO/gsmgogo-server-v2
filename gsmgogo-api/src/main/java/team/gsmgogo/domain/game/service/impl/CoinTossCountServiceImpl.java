package team.gsmgogo.domain.game.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.game.entity.GameEntity;
import team.gsmgogo.domain.game.repository.GameJpaRepository;
import team.gsmgogo.domain.game.service.CoinTossCountService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.facade.UserFacade;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoinTossCountServiceImpl implements CoinTossCountService {

    private final GameJpaRepository gameJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional(readOnly = true)
    public Integer coinTossCount() {
        UserEntity currentUser = userFacade.getCurrentUser();
        Optional<GameEntity> game = gameJpaRepository.findByUser(currentUser);

        return game.isPresent() ? game.get().getCoinToss() : 10;
    }
}
