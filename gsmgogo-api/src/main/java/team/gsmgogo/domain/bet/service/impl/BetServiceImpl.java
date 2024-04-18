package team.gsmgogo.domain.bet.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.gsmgogo.domain.bet.controller.dto.BetRequest;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.domain.bet.service.BetService;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.match.repository.MatchJpaRepository;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {
    private final BetJpaRepository betJpaRepository;
    private final MatchJpaRepository matchJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional
    public void execute(BetRequest betRequest) {
        MatchEntity betMatch = matchJpaRepository.getReferenceById(betRequest.getMatchId());

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime bettingStartTime = betMatch.getStartAt().minus(1, ChronoUnit.DAYS);
        LocalDateTime bettingEndTime = betMatch.getStartAt().minusMinutes(5);

        if (currentTime.isBefore(bettingStartTime) || currentTime.isAfter(bettingEndTime)) {
            throw new ExpectedException("배팅은 경기 하루 전부터 경기 시작 5분 전 사이에만 가능합니다.", HttpStatus.BAD_REQUEST);
        }

        TeamEntity betTeam = betRequest.getTeamAScore() > betRequest.getTeamBScore() ? betMatch.getTeamA() : betMatch.getTeamB();
        UserEntity currentUser = userFacade.getCurrentUser();

        if(betJpaRepository.existsByUserAndMatch(currentUser, betMatch)){
            throw new ExpectedException("이미 해당 경기에 배팅을 했습니다.", HttpStatus.BAD_REQUEST);
        }

        if (!currentUser.betPoint(betRequest.getBetPoint().intValue())) {
            throw new ExpectedException("보유 포인트 보다 더 많이 배팅할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        if (betRequest.getTeamAScore() > betRequest.getTeamBScore()) {
            betMatch.teamABetPoint(betRequest.getBetPoint().intValue());
        } else {
            betMatch.teamBBetPoint(betRequest.getBetPoint().intValue());
        }

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
