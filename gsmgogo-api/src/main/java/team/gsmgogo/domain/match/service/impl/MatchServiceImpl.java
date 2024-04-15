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
                return MatchInfoDto.builder()
                    .matchId(match.getMatchId())
                    .matchType(match.getMatchType())
                    .matchLevel(match.getMatchLevel())
                    .teamAId(match.getTeamA().getTeamId())
                    .teamAName(match.getTeamA().getTeamName())
                    .teamAGrade(match.getTeamAGrade())
                    .teamAClassType(match.getTeamAClassType())
                    .teamBId(match.getTeamB().getTeamId())
                    .teamBName(match.getTeamB().getTeamName())
                    .teamBGrade(match.getTeamBGrade())
                    .teamBClassType(match.getTeamBClassType())
                    .matchStartAt(match.getStartAt())
                    .matchEndAt(match.getEndAt())
                    .isVote(bettings.stream().anyMatch(bet -> bet.getMatch() == match))
                    .teamABet(match.getTeamABet())
                    .teamBBet(match.getTeamBBet())
                    .build();
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

                return MatchResultDto.builder()
                    .matchId(match.getMatchId())
                    .matchType(match.getMatchType())
                    .matchLevel(match.getMatchLevel())
                    .teamAId(match.getTeamA().getTeamId())
                    .teamAName(match.getTeamA().getTeamName())
                    .teamAGrade(match.getTeamAGrade())
                    .teamAClassType(match.getTeamAClassType())
                    .teamBId(match.getTeamB().getTeamId())
                    .teamBName(match.getTeamB().getTeamName())
                    .teamBGrade(match.getTeamBGrade())
                    .teamBClassType(match.getTeamBClassType())
                    .isVote(betting.isPresent())
                    .teamABet(match.getTeamABet())
                    .teamBBet(match.getTeamBBet())
                    .teamAScore(matchResult.getTeamAScore())
                    .teamBScore(matchResult.getTeamBScore())
                    .earnedPoint(calculatePoint.getEarnedPoint())
                    .losePoint(calculatePoint.getLosePoint())
                    .build();
            }).toList();

        return new MatchResponse(matchList, endedMatches);
    }
}
