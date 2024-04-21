package team.gsmgogo.domain.match.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
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
            .fetch();
    }
}
