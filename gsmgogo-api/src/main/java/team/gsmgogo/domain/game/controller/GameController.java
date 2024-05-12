package team.gsmgogo.domain.game.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.gsmgogo.domain.game.controller.dto.request.ButtonGameRequest;
import team.gsmgogo.domain.game.controller.dto.request.CoinRequest;
import team.gsmgogo.domain.game.controller.dto.response.ButtonGameResponse;
import team.gsmgogo.domain.game.controller.dto.response.CoinCountResponse;
import team.gsmgogo.domain.game.controller.dto.response.CoinResponse;
import team.gsmgogo.domain.game.controller.dto.response.DailyRouletteResponse;
import team.gsmgogo.domain.game.service.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final CoinTossService coinTossService;
    private final DailyRouletteRollService dailyRouletteRollService;
    private final CoinTossCountService coinTossCountService;
    private final ButtonGameService buttonGameService;
    private final ButtonGameStateService buttonGameStateService;

    @PostMapping("/roulette")
    public ResponseEntity<DailyRouletteResponse> dailyRouletteRoll() {
        return ResponseEntity.ok(dailyRouletteRollService.roll());
    }

    @PostMapping("/coin")
    public ResponseEntity<CoinResponse> coinToss(@RequestBody @Valid CoinRequest coinRequest) {
        return ResponseEntity.ok(coinTossService.execute(coinRequest));
    }

    @GetMapping("/coin")
    public ResponseEntity<CoinCountResponse> coinCount() {
        return ResponseEntity.ok(new CoinCountResponse(coinTossCountService.coinTossCount()));
    }

    @PostMapping("/button")
    public ResponseEntity<Void> clickButton(@RequestBody ButtonGameRequest buttonGameRequest) {
        buttonGameService.execute(buttonGameRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/button")
    public ResponseEntity<ButtonGameResponse> stateButton() {
        return ResponseEntity.ok(buttonGameStateService.execute());
    }
}
