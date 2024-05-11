package team.gsmgogo.domain.game.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.buttongame.entity.ButtonGameEntity;
import team.gsmgogo.domain.buttongame.repository.ButtonGameRepository;
import team.gsmgogo.domain.buttongameparticipate.entity.ButtonGameParticipate;
import team.gsmgogo.domain.buttongameparticipate.repository.ButtonGameParticipateRepository;
import team.gsmgogo.domain.game.controller.dto.response.ButtonGameResponse;
import team.gsmgogo.domain.game.service.ButtonGameStateService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class ButtonGameStateServiceImpl implements ButtonGameStateService {

    private final UserFacade userFacade;
    private final ButtonGameRepository buttonGameRepository;
    private final ButtonGameParticipateRepository buttonGameParticipateRepository;

    @Override
    public ButtonGameResponse execute() {

        UserEntity currentUser = userFacade.getCurrentUser();

        ButtonGameEntity buttonGame = buttonGameRepository.findByIsActiveIsTrue().orElse(null);
        ButtonGameParticipate buttonGameParticipate = buttonGameParticipateRepository
                .findByButtonGameAndUser(buttonGame, currentUser).orElse(null);

        if (buttonGame == null || buttonGameParticipate == null) return new ButtonGameResponse(null);

        return new ButtonGameResponse(buttonGameParticipate.getButtonType());
    }
}
