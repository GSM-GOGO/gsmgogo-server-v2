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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ButtonGameStateServiceImpl implements ButtonGameStateService {

    private final UserFacade userFacade;
    private final ButtonGameRepository buttonGameRepository;
    private final ButtonGameParticipateRepository buttonGameParticipateRepository;
    private final ButtonGameQueryDslRepository buttonGameQueryDslRepository;

    @Override
    public ButtonGameResponse execute(int month, int day) {

        UserEntity currentUser = userFacade.getCurrentUser();

        ButtonGameEntity findButtonGame = buttonGameQueryDslRepository.findByMonthAndDay(month, day)
                .orElseThrow(() -> new ExpectedException("버튼게임을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        ButtonGameParticipate myButtonGameParticipate = Objects.requireNonNull(findButtonGame.getParticipates()
                .stream()
                .filter(participate -> participate.getUser() == currentUser)
                .findFirst().orElse(null));


        Map<ButtonType, Integer> typeMap = Map.of(
                ButtonType.ONE, 0,
                ButtonType.TWO, 0,
                ButtonType.THREE, 0,
                ButtonType.FOUR, 0,
                ButtonType.FIVE, 0
        );

        List<Integer> results = List.of();
        typeMap.forEach((type, value) -> {

            Integer typeParticipateSize = findButtonGame.getParticipates().stream()
                    .filter(p -> p.getButtonType().equals(type)
                    ).toList().size();

                    results.add(typeParticipateSize);
                    typeMap.put(type, typeParticipateSize);
                }
        );

        boolean isWin = findButtonGame.getWinType() == myButtonGameParticipate.getButtonType();

        return ButtonGameResponse.builder()
                .buttonType(myButtonGameParticipate.getButtonType())
                .date(findButtonGame.getCreateDate())
                .isActive(findButtonGame.getIsActive())
                .results(results)
                .winType(findButtonGame.getWinType())
                .isWin(isWin)
                .earnedPoint(isWin ?
                            (int) Math.ceil((double) 2_000_000 / typeMap.get(findButtonGame.getWinType())) : null
                        )
                .build();
    }
}
