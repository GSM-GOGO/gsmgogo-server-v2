package team.gsmgogo.domain.game.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoinStatus {
    HEAD("HEAD"),
    TAIL("TAIL");

    private final String direction;
}
