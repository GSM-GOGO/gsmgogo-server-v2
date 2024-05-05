package team.gsmgogo.domain.match.repository;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.CollectionExpression;
import com.querydsl.core.types.SubQueryExpression;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.match.entity.QMatchEntity;

@Repository
@RequiredArgsConstructor
public class MatchQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<MatchEntity> findByMonthAndDay(int month, int day) {
        QMatchEntity match = QMatchEntity.matchEntity;
        return queryFactory
            .selectFrom(match)
            .where(
                match.startAt.month().eq(month)
                .and(match.startAt.dayOfMonth().eq(day))
                .and(match.isEnd.eq(false))
            )
            .orderBy(match.startAt.asc())
            .fetch();
    }

    public List<MatchEntity> findByMatchId(List<MatchEntity> matchesInput) {
        QMatchEntity match = QMatchEntity.matchEntity;
        return queryFactory
            .selectFrom(match)
            .where(
                match.in(matchesInput)
                .and(match.isEnd.eq(false))
            )
            .orderBy(match.startAt.asc())
            .fetch();
    }
}
