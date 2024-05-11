package team.gsmgogo.domain.buttongameparticipate.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.gsmgogo.domain.buttongame.entity.ButtonGameEntity;
import team.gsmgogo.domain.buttongame.enums.ButtonType;
import team.gsmgogo.domain.buttongameparticipate.repository.dto.ButtonGameResultDto;
import team.gsmgogo.domain.user.entity.UserEntity;

import java.util.List;

import static team.gsmgogo.domain.buttongameparticipate.entity.QButtonGameParticipate.buttonGameParticipate;

@Repository
@RequiredArgsConstructor
public class ButtonGameParticipateQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public ButtonGameResultDto findWinUserList(ButtonGameEntity buttonGame) {
        ButtonType winType = findWinType(buttonGame);
        List<UserEntity> winUserList = winUserList(buttonGame, winType);

        return new ButtonGameResultDto(winType, winUserList);
    }

    private ButtonType findWinType(ButtonGameEntity buttonGame) {
        return queryFactory
                .select(buttonGameParticipate.buttonType)
                .from(buttonGameParticipate)
                .where(buttonGameParticipate.buttonGame.eq(buttonGame))
                .groupBy(buttonGameParticipate.buttonType)
                .orderBy(buttonGameParticipate.count().asc())
                .fetchFirst();
    }

    private List<UserEntity> winUserList(ButtonGameEntity buttonGame, ButtonType winType) {
        return queryFactory
                .select(buttonGameParticipate.user)
                .from(buttonGameParticipate)
                .where(buttonGameParticipate.buttonGame.eq(buttonGame).and(
                        buttonGameParticipate.buttonType.eq(winType))
                ).fetch();
    }

}
