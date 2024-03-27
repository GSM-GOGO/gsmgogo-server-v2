package team.gsmgogo.domain.normalteamparticipate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NormalTeamType {
    TOSS_RUN, // 이어달리기
    MISSION_RUN, // 미션달리기
    TUG_OF_WAR, // 줄다리기
    FREE_THROW, // 자유투 릴레이
    GROUP_ROPE_JUMP // 단체 줄넘기
}
