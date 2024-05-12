package team.gsmgogo.domain.buttongame.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static team.gsmgogo.domain.buttongame.entity.QButtonGameEntity.buttonGameEntity;

import team.gsmgogo.domain.buttongame.entity.ButtonGameEntity;

import static team.gsmgogo.domain.buttongameparticipate.entity.QButtonGameParticipate.buttonGameParticipate;

@Repository
@RequiredArgsConstructor
public class ButtonGameQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public ButtonGameEntity findByMonthAndDay(int month, int day) {
        return queryFactory
                .selectFrom(buttonGameEntity)
                .join(buttonGameEntity.participates, buttonGameParticipate).fetchJoin()
                .where(
                        buttonGameEntity.createDate.month().eq(month)
                        .and(buttonGameEntity.createDate.dayOfMonth().eq(day))
                )
                .fetchOne();
    }
}
