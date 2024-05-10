package team.gsmgogo.domain.tax.common.util;

import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.user.entity.UserEntity;

import java.util.List;

/**
 * userEntity의 point에 관련된 util클래스입니다.
 */
public class UserPointUtil {

    /**
     * userEntity의 총 포인트를 합산해서 반환해주는 메서드입니다.
     * @param user 포인트를 계산할 유저
     * @param bets 유저가 배팅한 BetEntity 리스트
     * @return 유저의 총 포인트를 반환
     */
    public static Integer calculateUserPoint(UserEntity user, List<BetEntity> bets) {
        int userPoint = user.getPoint();

        //정산되지않은 게임에 포인트를 배팅한 유저의 BetEntity를 필터링
        List<BetEntity> userBets = filterBetsWhereMatchIsNotEnded(bets);

        //유저의 배팅 포인트를 유저의 총 포인트에 합산
        for (BetEntity userBet : userBets) {
            userPoint += userBet.getBetPoint();
        }

        return userPoint;
    }

    /**
     * 경기 포인트 정산이 끝나지 않은 Bet 객체만 필터링 해주는 메서드입니다.
     * @param bets BetEntity 리스트
     * @return 필터링된 BetEntity 리스트
     */
    public static List<BetEntity> filterBetsWhereMatchIsNotEnded(List<BetEntity> bets) {
        return bets.stream()
                .filter(bet -> !bet.getMatch().getIsEnd())
                .toList();

    }
}
