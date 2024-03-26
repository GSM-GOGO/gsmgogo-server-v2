package team.gsmgogo.domain.normalteamparticipate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NormalTeamType {

    TOSS_RUN("TOSS_RUN"), // 이어달리기
    TUG_OF_WAR("TUG_OF_WAR"); // 줄다리기

    private final String type;
}
