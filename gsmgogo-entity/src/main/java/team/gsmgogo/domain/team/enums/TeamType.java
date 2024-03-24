package team.gsmgogo.domain.team.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TeamType {

    SOCCER("SOCCER"),
    BASKETBALL("BASKETBALL"),
    VOLLEYBALL("VOLLEYBALL"),
    TOSS_RUN("TOSS_RUN");

    private final String type;
}
