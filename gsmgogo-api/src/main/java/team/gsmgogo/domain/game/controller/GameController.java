package team.gsmgogo.domain.game.controller;

import feign.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.gsmgogo.domain.game.controller.dto.request.CoinRequest;
import team.gsmgogo.domain.game.controller.dto.response.CoinCountResponse;
import team.gsmgogo.domain.game.controller.dto.response.CoinResponse;
import team.gsmgogo.domain.game.controller.dto.response.DailyRouletteResponse;
import team.gsmgogo.domain.game.service.CoinTossCountService;
import team.gsmgogo.domain.game.service.CoinTossService;
import team.gsmgogo.domain.game.service.DailyRouletteRollService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final CoinTossService coinTossService;
    private final DailyRouletteRollService dailyRouletteRollService;
    private final CoinTossCountService coinTossCountService;

    @PostMapping("/roulette")
    public ResponseEntity<DailyRouletteResponse> dailyRouletteRoll() {
        return ResponseEntity.ok(dailyRouletteRollService.roll());
    }

    @PostMapping("/coin")
    public ResponseEntity<CoinResponse> coinToss(@RequestBody @Valid CoinRequest coinRequest){
        return ResponseEntity.ok(coinTossService.execute(coinRequest));
    }

    @GetMapping("/coin")
    public ResponseEntity<CoinCountResponse> coinCount() {
        return ResponseEntity.ok(new CoinCountResponse(coinTossCountService.coinTossCount()));
    }
}
