package team.gsmgogo.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.bet.repository.BetJpaRepository;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.match.repository.MatchQueryDslRepository;
import team.gsmgogo.domain.matchresult.entity.MatchResultEntity;
import team.gsmgogo.domain.matchresult.repository.MatchResultQueryDslRepository;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.user.dto.response.BetMatchInfoDto;
import team.gsmgogo.domain.user.dto.response.BetMatchResponse;
import team.gsmgogo.domain.user.dto.response.BetMatchResultDto;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.service.QueryUserBetMatchService;
import team.gsmgogo.global.common.CalculatePoint;
import team.gsmgogo.global.common.CalculatePointRequest;
import team.gsmgogo.global.common.CalculatePointResponse;
import team.gsmgogo.global.facade.UserFacade;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryUserBetMatchServiceImpl implements QueryUserBetMatchService {
    private final UserFacade userFacade;
    private final BetJpaRepository betJpaRepository;
    private final MatchQueryDslRepository matchQueryDslRepository;
    private final MatchResultQueryDslRepository matchResultQueryDslRepository;

    @Override
    @Transactional(readOnly = true)
    public BetMatchResponse execute() {
        UserEntity currentUser = userFacade.getCurrentUser();
        List<BetEntity> bets = betJpaRepository.findByUser(currentUser);

        Set<MatchEntity> matches = new LinkedHashSet<>();
        Set<MatchResultEntity> matchResults = new LinkedHashSet<>();

        bets.forEach(betEntity -> {
            Optional<MatchEntity> addMatch = matchQueryDslRepository.findByMatchId(betEntity.getMatch().getMatchId());
            Optional<MatchResultEntity> addMatchResult = matchResultQueryDslRepository.findByMatchId(betEntity.getMatch().getMatchId());

            if(addMatchResult.isPresent()) matchResults.add(addMatchResult.get());
            if(addMatch.isPresent()) matches.add(addMatch.get());
        });

        List<BetMatchInfoDto> matchList = matches.stream()
            .map(match -> BetMatchInfoDto.builder()
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
                    match.getTeamA() != null && match.getTeamA().getTeamType() == TeamType.BADMINTON ?
                        match.getTeamA().getTeamParticipates().get(0).getUser().getUserName() + "/" + match.getTeamA().getTeamParticipates().get(1).getUser().getUserName()
                        : null
                )
                .badmintonBParticipateNames(
                    match.getTeamB() != null && match.getTeamB().getTeamType() == TeamType.BADMINTON ?
                        match.getTeamB().getTeamParticipates().get(0).getUser().getUserName() + "/" + match.getTeamB().getTeamParticipates().get(1).getUser().getUserName()
                        : null
                )
                .matchStartAt(match.getStartAt())
                .matchEndAt(match.getEndAt())
                .teamABet(match.getTeamABet())
                .teamBBet(match.getTeamBBet())
                .build()
            ).toList();

        List<BetMatchResultDto> endedMatches = matchResults.stream()
            .map(matchResult -> {
                MatchEntity match = matchResult.getMatch();
                BetEntity betting = bets.stream()
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

                Long isParticipateTeamId =
                    match.getTeamA().getTeamParticipates().stream().anyMatch(participate ->
                        participate.getUser().getUserId().equals(currentUser.getUserId())) ? match.getTeamA().getTeamId() :
                        match.getTeamB().getTeamParticipates().stream().anyMatch(participate ->
                            participate.getUser().getUserId().equals(currentUser.getUserId())) ? match.getTeamB().getTeamId() : null;

                return BetMatchResultDto.builder()
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
                        match.getTeamA() != null && match.getTeamA().getTeamType() == TeamType.BADMINTON ?
                            match.getTeamA().getTeamParticipates().get(0).getUser().getUserName() + "/" + match.getTeamA().getTeamParticipates().get(1).getUser().getUserName()
                            : null
                    )
                    .badmintonBParticipateNames(
                        match.getTeamB() != null && match.getTeamB().getTeamType() == TeamType.BADMINTON ?
                            match.getTeamB().getTeamParticipates().get(0).getUser().getUserName() + "/" + match.getTeamB().getTeamParticipates().get(1).getUser().getUserName()
                            : null
                    )
                    .teamABet(match.getTeamABet())
                    .teamBBet(match.getTeamBBet())
                    .teamAScore(matchResult.getTeamAScore())
                    .teamBScore(matchResult.getTeamBScore())
                    .earnedPoint(calculatePoint.getEarnedPoint())
                    .losePoint(calculatePoint.getLosePoint())
                    .betTeamAScore(betting != null ? betting.getBetScoreA() : null)
                    .betTeamBScore(betting != null ? betting.getBetScoreB() : null)
                    .isParticipateTeamId(isParticipateTeamId)
                    .participateEarnedPoint(
                        isParticipateTeamId != null
                            // 소속된 팀의 승리 여부
                            ? isParticipateTeamId.equals(matchResult.getTeamAScore() > matchResult.getTeamBScore()
                            ? match.getTeamA().getTeamId() : match.getTeamB().getTeamId())
                            // 소속됨 팀의 승리 여부에 따라 ? 얻은 포인트 : 0
                            ? (int) Math.ceil((match.getTeamABet() + match.getTeamBBet()) * 0.005) : 0
                            : null)
                    .build();
            }).toList();

        return new BetMatchResponse(matchList, endedMatches);
    }
}
