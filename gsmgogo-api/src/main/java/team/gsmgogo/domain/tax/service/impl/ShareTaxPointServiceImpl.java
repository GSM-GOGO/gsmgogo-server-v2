package team.gsmgogo.domain.tax.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.domain.tax.common.util.UserPointUtil;
import team.gsmgogo.domain.tax.service.ShareTaxPointService;
import team.gsmgogo.domain.tax.service.dto.TaxCollectionServiceReturnInfoDto;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareTaxPointServiceImpl implements ShareTaxPointService {

    private final UserJpaRepository userJpaRepository;
    private final BetJpaRepository betJpaRepository;

    /**
     * 모인 세금으로 지원금을 배부해주는 메서드입니다.
     * @param dto TaxCollectionService의 리턴값을 담는 dto입니다.
     */
    @Override
    public void shareTaxPoint(TaxCollectionServiceReturnInfoDto dto) {

        List<UserEntity> users = userJpaRepository.findAll();
        List<BetEntity> bets = betJpaRepository.findAll();

        users.parallelStream().forEach(user -> {
            //유저의 총 포인트
            int userPoint = UserPointUtil.calculateUserPoint(user, bets);

            if (userPoint != 0) {
                //역으로 분배 해주기 위해서 100에서 자신의 포인트 비율을 빼주기
                int pointRate = 100 - ((userPoint / dto.allPoints()) * 100);

                //걷은 세금에서 지원금 배분
                int reliefFunds = dto.allTaxes() * pointRate / 100;
                user.addPoint(reliefFunds);
            } else {
                //포인트가 0인 유저는 지원금 2만포인트 지급
                user.addPoint(20000);
            }
        });

        userJpaRepository.saveAll(users);
    }
}
