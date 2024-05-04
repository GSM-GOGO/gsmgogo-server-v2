package team.gsmgogo.domain.match.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
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
            .orderBy(match.startAt.asc())
            .fetch();
    }

    public Optional<MatchEntity> findByMatchId(Long matchId) {
        QMatchEntity match = QMatchEntity.matchEntity;
        List<MatchEntity> matches = queryFactory
            .selectFrom(match)
            .where(
                match.matchId.eq(matchId)
                .and(match.isEnd.eq(false))
            )
            .orderBy(match.startAt.asc())
            .fetch();

        if(matches.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(matches.get(0));
        }
    }
}
