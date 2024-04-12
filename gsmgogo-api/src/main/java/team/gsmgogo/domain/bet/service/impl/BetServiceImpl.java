package team.gsmgogo.domain.bet.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.bet.controller.dto.BetRequest;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.domain.bet.service.BetService;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.match.repository.MatchJpaRepository;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {
    private final BetJpaRepository betJpaRepository;
    private final MatchJpaRepository matchJpaRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional
    public void execute(BetRequest betRequest) {
        MatchEntity betMatch = matchJpaRepository.getReferenceById(betRequest.getMatchId());
        TeamEntity betTeam = teamJpaRepository.getReferenceById(betRequest.getTeamId());
        UserEntity currentUser = userFacade.getCurrentUser();

        BetEntity bet = BetEntity.builder()
            .match(betMatch)
            .team(betTeam)
            .betScoreA(betRequest.getTeamAScore())
            .betScoreB(betRequest.getTeamBScore())
            .betPoint(betRequest.getBetPoint())
            .user(currentUser)
            .build();

        betJpaRepository.save(bet);
    }
}
