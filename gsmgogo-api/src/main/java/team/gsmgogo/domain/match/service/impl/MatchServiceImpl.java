package team.gsmgogo.domain.match.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.domain.match.controller.dto.response.MatchInfoDto;
import team.gsmgogo.domain.match.controller.dto.response.MatchResponse;
import team.gsmgogo.domain.match.controller.dto.response.MatchResultDto;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.match.repository.MatchQueryDslRepository;
import team.gsmgogo.domain.match.service.MatchService;
import team.gsmgogo.domain.matchresult.entity.MatchResultEntity;
import team.gsmgogo.domain.matchresult.repository.MatchResultQueryDslRepository;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.global.common.CalculatePoint;
import team.gsmgogo.global.common.CalculatePointRequest;
import team.gsmgogo.global.common.CalculatePointResponse;
import team.gsmgogo.global.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchQueryDslRepository matchQueryDslRepository;
    private final MatchResultQueryDslRepository matchResultQueryDslRepository;
    private final BetJpaRepository betJpaRepository;
    private final UserFacade userFacade;

    @Override
    public MatchResponse execute(int month, int day) {
        UserEntity currentUser = userFacade.getCurrentUser();
        List<MatchEntity> matches = matchQueryDslRepository.findByMonthAndDay(month, day);
        List<MatchResultEntity> matchResults = matchResultQueryDslRepository.findByMonthAndDay(month, day);
        List<BetEntity> bettings = betJpaRepository.findByUser(currentUser);

        List<MatchInfoDto> matchList = matches.stream()
            .filter(match -> match.getEndAt().isBefore(LocalDateTime.now()))
            .map(match -> {
                return new MatchInfoDto(
                    match.getMatchId(),
                    match.getMatchType(),
                    match.getMatchLevel(),
                    match.getTeamA().getTeamId(),
                    match.getTeamA().getTeamName(),
                    match.getTeamAGrade(),
                    match.getTeamAClassType(),
                    match.getTeamB().getTeamId(),
                    match.getTeamB().getTeamName(),
                    match.getTeamBGrade(),
                    match.getTeamBClassType(),
                    match.getStartAt(),
                    match.getEndAt(),
                    bettings.stream().anyMatch(bet -> bet.getMatch() == match),
                    match.getTeamABet(),
                    match.getTeamBBet()
                );
            }).toList();

        List<MatchResultDto> endedMatches = matchResults.stream()
            .map(matchResult -> {
                MatchEntity match = matchResult.getMatch();
                Optional<BetEntity> betting = bettings.stream()
                    .filter(bet -> bet.getMatch() == match)
                    .findFirst();

                CalculatePointRequest request = new CalculatePointRequest(
                    betting.get().getBetPoint(), 
                    matchResult.getTeamAScore(), 
                    matchResult.getTeamBScore(), 
                    betting.get().getBetScoreA(), 
                    betting.get().getBetScoreB(), 
                    match.getTeamABet(), 
                    match.getTeamBBet()
                );
                CalculatePointResponse calculatePoint = new CalculatePoint().execute(request);

                return new MatchResultDto(
                    match.getMatchId(), 
                    match.getMatchType(), 
                    match.getMatchLevel(), 
                    match.getTeamA().getTeamId(), 
                    match.getTeamA().getTeamName(), 
                    match.getTeamAGrade(), 
                    match.getTeamAClassType(), 
                    match.getTeamB().getTeamId(), 
                    match.getTeamB().getTeamName(), 
                    match.getTeamBGrade(), 
                    match.getTeamBClassType(), 
                    betting.isPresent(), 
                    match.getTeamABet(), 
                    match.getTeamBBet(), 
                    matchResult.getTeamAScore(), 
                    matchResult.getTeamBScore(), 
                    betting.get().getBetScoreA(), 
                    betting.get().getBetScoreB(), 
                    calculatePoint.getEarnedPoint(), 
                    calculatePoint.getLosePoint()
                );
            }).toList();

        return new MatchResponse(matchList, endedMatches);
    }
}
