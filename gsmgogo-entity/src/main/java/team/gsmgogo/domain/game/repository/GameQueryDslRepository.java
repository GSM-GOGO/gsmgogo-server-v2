package team.gsmgogo.domain.game.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static team.gsmgogo.domain.game.entity.QGameEntity.gameEntity;

@Repository
@RequiredArgsConstructor
public class GameQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public void bulkResetGameCount() {
        queryFactory.update(gameEntity)
                    .set(gameEntity.coinToss, 0)
                    .set(gameEntity.dailyRoulette, false)
                .execute();
    }
}
