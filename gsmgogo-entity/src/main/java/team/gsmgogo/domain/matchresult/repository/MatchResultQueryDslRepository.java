package team.gsmgogo.domain.matchresult.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team.gsmgogo.domain.matchresult.entity.MatchResultEntity;
import team.gsmgogo.domain.matchresult.entity.QMatchResultEntity;

@Repository
@RequiredArgsConstructor
public class MatchResultQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<MatchResultEntity> findByMonthAndDay(int month, int day) {
        QMatchResultEntity matchResult = QMatchResultEntity.matchResultEntity;
        return queryFactory
            .selectFrom(matchResult)
            .where(matchResult.match.startAt.month().eq(month).and(matchResult.match.startAt.dayOfMonth().eq(day)))
            .fetch();
    }

    public Optional<MatchResultEntity> findByMatchId(Long matchId){
        QMatchResultEntity matchResult = QMatchResultEntity.matchResultEntity;
        List<MatchResultEntity> matches = queryFactory
            .selectFrom(matchResult)
            .where(matchResult.match.matchId.eq(matchId))
            .orderBy(matchResult.match.startAt.asc())
            .fetch();

        if(matches.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(matches.get(0));
        }
    }
}
