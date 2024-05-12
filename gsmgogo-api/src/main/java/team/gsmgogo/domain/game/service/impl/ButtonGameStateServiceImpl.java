package team.gsmgogo.domain.game.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.buttongame.entity.ButtonGameEntity;
import team.gsmgogo.domain.buttongame.enums.ButtonType;
import team.gsmgogo.domain.buttongame.repository.ButtonGameQueryDslRepository;
import team.gsmgogo.domain.buttongame.repository.ButtonGameRepository;
import team.gsmgogo.domain.buttongameparticipate.entity.ButtonGameParticipate;
import team.gsmgogo.domain.buttongameparticipate.repository.ButtonGameParticipateRepository;
import team.gsmgogo.domain.game.controller.dto.response.ButtonGameResponse;
import team.gsmgogo.domain.game.service.ButtonGameStateService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ButtonGameStateServiceImpl implements ButtonGameStateService {

    private final UserFacade userFacade;
    private final ButtonGameQueryDslRepository buttonGameQueryDslRepository;

    @Override
    public ButtonGameResponse execute(int month, int day) {

        UserEntity currentUser = userFacade.getCurrentUser();

        ButtonGameEntity findButtonGame = buttonGameQueryDslRepository.findByMonthAndDay(month, day)
                .orElseThrow(() -> new ExpectedException("버튼게임을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        System.out.println(findButtonGame.getParticipates().size());

        ButtonGameParticipate myButtonGameParticipate = findButtonGame.getParticipates()
                .stream()
                .filter(p -> p.getUser().getUserId().equals(currentUser.getUserId()))
                .findAny().orElse(null);

        if (myButtonGameParticipate == null)
            return ButtonGameResponse.builder()
                    .buttonType(null)
                    .date(findButtonGame.getCreateDate())
                    .isActive(findButtonGame.getIsActive())
                    .results(null)
                    .isWin(null)
                    .winType(null)
                    .earnedPoint(null)
                    .build();

        Map<ButtonType, Integer> typeMap = Map.of(
                ButtonType.ONE, 0,
                ButtonType.TWO, 0,
                ButtonType.THREE, 0,
                ButtonType.FOUR, 0,
                ButtonType.FIVE, 0
        );

        Map<ButtonType, Integer> updatedTypeMap = new HashMap<>(typeMap);
        typeMap.forEach((type, value) -> {
            Integer typeParticipateSize = (int) findButtonGame.getParticipates().stream()
                    .filter(p -> p.getButtonType().equals(type)
                    ).count();

                    updatedTypeMap.put(type, typeParticipateSize);
                }
        );

        boolean isWin = findButtonGame.getWinType() == myButtonGameParticipate.getButtonType();

        return ButtonGameResponse.builder()
                .buttonType(myButtonGameParticipate.getButtonType())
                .date(findButtonGame.getCreateDate())
                .isActive(findButtonGame.getIsActive())
                .results(!findButtonGame.getIsActive() ? updatedTypeMap : null)
                .winType(findButtonGame.getWinType())
                .isWin(findButtonGame.getWinType() == null ? null : isWin)
                .earnedPoint(isWin ?
                            (int) Math.ceil((double) 2_000_000 / updatedTypeMap.get(findButtonGame.getWinType())) : null
                        )
                .build();
    }
}
