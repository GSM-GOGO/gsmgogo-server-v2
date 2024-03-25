package team.gsmgogo.domain.team.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TeamType {

    SOCCER("SOCCER"),
    BASKETBALL("BASKETBALL"),
    VOLLEYBALL("VOLLEYBALL"),
    NOMAL("NOMAL"); // 일반경기

    private final String type;
}
