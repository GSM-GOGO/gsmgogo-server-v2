package team.gsmgogo.domain.game.controller;

import feign.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.gsmgogo.domain.game.controller.dto.request.CoinRequest;
import team.gsmgogo.domain.game.controller.dto.response.CoinResponse;
import team.gsmgogo.domain.game.controller.dto.response.DailyRouletteResponse;
import team.gsmgogo.domain.game.service.CoinTossService;
import team.gsmgogo.domain.game.service.DailyRouletteRoll;

import java.awt.image.RescaleOp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final DailyRouletteRoll dailyRouletteRoll;
    private final CoinTossService coinTossService;

    @PostMapping("/roulette")
    public ResponseEntity<DailyRouletteResponse> dailyRouletteRoll() {
        return ResponseEntity.ok(dailyRouletteRoll.roll());
    }

    @PostMapping("/coin")
    public ResponseEntity<CoinResponse> coinToss(@RequestBody @Valid CoinRequest coinRequest){
        return ResponseEntity.ok(coinTossService.execute(coinRequest));
    }
}
