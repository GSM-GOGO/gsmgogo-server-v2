package team.gsmgogo.domain.game.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.gsmgogo.domain.game.controller.dto.response.DailyRouletteResponse;
import team.gsmgogo.domain.game.service.DailyRouletteRollService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final DailyRouletteRollService dailyRouletteRollService;

    @PostMapping("/roulette")
    public ResponseEntity<DailyRouletteResponse> dailyRouletteRoll() {
        return ResponseEntity.ok(dailyRouletteRollService.roll());
    }

}
