package team.gsmgogo.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.gsmgogo.domain.follow.entity.QFollowEntity;
import team.gsmgogo.domain.team.entity.QTeamEntity;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.user.entity.QUserEntity;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.enums.IsVerify;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public void bulkResetVerifyCount() {
        QUserEntity user = QUserEntity.userEntity;
        queryFactory.update(user).set(user.verifyCount, 0L).execute();
    }

    public List<UserEntity> findByFollowsTeam(TeamEntity team){
        QUserEntity user = QUserEntity.userEntity;
        QFollowEntity follows = QFollowEntity.followEntity;

        return queryFactory
            .selectFrom(user)
            .join(user.follows, follows)
            .where(follows.team.eq(team).and(user.isVerify.eq(IsVerify.VERIFY)))
            .fetch();
    }
}
