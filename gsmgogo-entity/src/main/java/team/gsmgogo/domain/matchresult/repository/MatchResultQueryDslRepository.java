package team.gsmgogo.domain.matchresult.repository;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.SubQueryExpression;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team.gsmgogo.domain.match.entity.MatchEntity;
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

    public List<MatchResultEntity> findByMatchId(List<MatchEntity> matches){
        QMatchResultEntity matchResult = QMatchResultEntity.matchResultEntity;
        return queryFactory
            .selectFrom(matchResult)
            .where(matchResult.match.in(matches))
            .orderBy(matchResult.match.startAt.asc())
            .fetch();
    }
}
