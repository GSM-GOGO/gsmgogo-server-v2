package team.gsmgogo.domain.nomalteamparticipate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NomalTeamType {

    TOSS_RUN("TOSS_RUN"), // 이어달리기
    TUG_OF_WAR("TUG_OF_WAR"); // 줄다리기

    private final String type;
}
