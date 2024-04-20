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
import team.gsmgogo.domain.team.enums.TeamType;
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
            .map(match -> MatchInfoDto.builder()
                .matchId(match.getMatchId())
                .matchType(match.getMatchType())
                .matchLevel(match.getMatchLevel())
                .teamAId(match.getTeamA() != null ? match.getTeamA().getTeamId() : null)
                .teamAName(match.getTeamA() != null ? match.getTeamA().getTeamName() : "TBD")
                .teamAGrade(match.getTeamAGrade())
                .teamAClassType(match.getTeamAClassType())
                .teamBId(match.getTeamB() != null ? match.getTeamB().getTeamId() : null)
                .teamBName(match.getTeamB() != null ? match.getTeamB().getTeamName() : "TBD")
                .teamBGrade(match.getTeamBGrade())
                .teamBClassType(match.getTeamBClassType())
                .badmintonRank(
                        match.getTeamA() != null && match.getTeamA().getTeamType() == TeamType.BADMINTON ? match.getTeamA().getBadmintonRank() : null)
                    .badmintonAParticipateNames(
                            match.getTeamA().getTeamType() == TeamType.BADMINTON ?
                                    match.getTeamA().getTeamParticipates().get(0).getUser().getUserName() + "/" + match.getTeamA().getTeamParticipates().get(1).getUser().getUserName()
                                    : null
                    )
                    .badmintonBParticipateNames(
                            match.getTeamB().getTeamType() == TeamType.BADMINTON ?
                                    match.getTeamB().getTeamParticipates().get(0).getUser().getUserName() + "/" + match.getTeamB().getTeamParticipates().get(1).getUser().getUserName()
                                    : null
                    )
                .matchStartAt(match.getStartAt())
                .matchEndAt(match.getEndAt())
                .isVote(bettings.stream().anyMatch(bet -> bet.getMatch() == match))
                .teamABet(match.getTeamABet())
                .teamBBet(match.getTeamBBet())
                .build()).toList();

        List<MatchResultDto> endedMatches = matchResults.stream()
            .map(matchResult -> {
                MatchEntity match = matchResult.getMatch();
                BetEntity betting = bettings.stream()
                    .filter(bet -> bet.getMatch() == match)
                    .findFirst().orElse(null);

                CalculatePointResponse calculatePoint = new CalculatePointResponse();

                if (betting != null){
                    CalculatePointRequest request = new CalculatePointRequest(
                        betting.getBetPoint(),
                        matchResult.getTeamAScore(), 
                        matchResult.getTeamBScore(), 
                        betting.getBetScoreA(),
                        betting.getBetScoreB(),
                        match.getTeamABet(), 
                        match.getTeamBBet()
                    );
                    calculatePoint = new CalculatePoint().execute(request);
                }

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
                    .badmintonRank(match.getTeamA() != null && match.getTeamA().getTeamType() == TeamType.BADMINTON ? match.getTeamA().getBadmintonRank() : null)
                        .badmintonAParticipateNames(
                                match.getTeamA().getTeamType() == TeamType.BADMINTON ?
                                        match.getTeamA().getTeamParticipates().get(0).getUser().getUserName() + "/" + match.getTeamA().getTeamParticipates().get(1).getUser().getUserName()
                                        : null
                        )
                        .badmintonBParticipateNames(
                                match.getTeamB().getTeamType() == TeamType.BADMINTON ?
                                        match.getTeamB().getTeamParticipates().get(0).getUser().getUserName() + "/" + match.getTeamB().getTeamParticipates().get(1).getUser().getUserName()
                                        : null
                        )
                    .isVote(betting != null)
                    .teamABet(match.getTeamABet())
                    .teamBBet(match.getTeamBBet())
                    .teamAScore(matchResult.getTeamAScore())
                    .teamBScore(matchResult.getTeamBScore())
                    .earnedPoint(calculatePoint.getEarnedPoint())
                    .losePoint(calculatePoint.getLosePoint())
                    .betTeamAScore(betting != null ? betting.getBetScoreA() : null)
                    .betTeamBScore(betting != null ? betting.getBetScoreB() : null)
                    .build();
            }).toList();

        return new MatchResponse(matchList, endedMatches);
    }
}
