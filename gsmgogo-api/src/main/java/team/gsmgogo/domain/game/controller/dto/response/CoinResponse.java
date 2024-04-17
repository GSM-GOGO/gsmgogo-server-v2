package team.gsmgogo.domain.game.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.game.enums.CoinStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CoinResponse {
    private CoinStatus prediction;
    private CoinStatus result;
    private boolean win;
    private Integer earnedPoint;
}
