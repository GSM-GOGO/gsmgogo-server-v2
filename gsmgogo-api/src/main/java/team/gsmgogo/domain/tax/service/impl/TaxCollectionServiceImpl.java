package team.gsmgogo.domain.tax.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.domain.match.repository.MatchJpaRepository;
import team.gsmgogo.domain.tax.common.util.UserPointUtil;
import team.gsmgogo.domain.tax.entity.TaxEntity;
import team.gsmgogo.domain.tax.repository.TaxJpaRepository;
import team.gsmgogo.domain.tax.service.TaxCollectionService;
import team.gsmgogo.domain.tax.service.dto.TaxCollectionServiceReturnInfoDto;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxCollectionServiceImpl implements TaxCollectionService {

    private final UserJpaRepository userJpaRepository;
    private final MatchJpaRepository matchJpaRepository;
    private final BetJpaRepository betJpaRepository;
    private final TaxJpaRepository taxJpaRepository;

    //세금 10%
    private final static Integer TARIFF = 10 / 100;

    /**
     * 세금을 징수하는 메서드입니다.
     * @return 지원금 배부에 필요한 정보를 담는 dto
     */
    @Override
    @Transactional
    public TaxCollectionServiceReturnInfoDto taxCollection() {
        List<UserEntity> users = userJpaRepository.findAll();
        List<BetEntity> bets = betJpaRepository.findAll();
        List<TaxEntity> taxes = new ArrayList<>();

        users.parallelStream().forEach(user -> {
            //유저의 총 포인트
            int userPoint = UserPointUtil.calculateUserPoint(user, bets);

            //유저의 총포인트에 대한 세금 책정
            int taxPoint = userPoint * TARIFF;

            //유저 포인트에 세금 징수
            user.minusPointByTax(taxPoint);

            //유저가 낸 세금 객체 리스트에 담기
            TaxEntity taxEntity = TaxEntity.builder()
                    .taxPoint(taxPoint)
                    .user(user)
                    .build();
            taxes.add(taxEntity);

            //세금 징수후 유저의 포인트가 음수일 경우 배팅된 게임에서 포인트를 차감하기 위한 로직
            List<BetEntity> userBets = UserPointUtil.getUserBets(bets);
            if (user.getPoint() < 0){

                //포인트가 음수인 상태니 0으로 초기화
                int lackPoint = user.getPoint() * -1;
                user.addPoint(lackPoint);

                //유저가 배팅한 포인트 합산
                int userAllBetPoint = 0;
                for (BetEntity userBet : userBets) {
                    userAllBetPoint += userBet.getBetPoint();
                }

                //정산되지않은 게임에 포인트를 배팅한 유저의 BetEntity에서 차감할 포인트 비를 구하고 차감
                for (BetEntity userBet : userBets) {
                    int minusPointRate = (userBet.getBetPoint().intValue() / userAllBetPoint) * 100;
                    int minusPoint = lackPoint * minusPointRate / 100;
                    userBet.minusBetPointByTax(minusPoint);
                }

                betJpaRepository.saveAll(bets);
            }
        });

        //세금 총 합산
        int allTaxes = taxJpaRepository.saveAll(taxes).stream()
                .mapToInt(TaxEntity::getTaxPoint)
                .sum();

        //서버에 풀린 모든 포인트
        int allPoints = userJpaRepository.sumAllPoints() + matchJpaRepository.sumTeamAAndTeamBBetPoints();

        return TaxCollectionServiceReturnInfoDto.builder()
                .users(users)
                .allPoints(allPoints)
                .allTaxes(allTaxes)
                .build();
    }
}
