package team.gsmgogo.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.gsmgogo.domain.user.entity.QUserEntity;

@Repository
@RequiredArgsConstructor
public class UserQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public void bulkResetVerifyCount() {
        QUserEntity user = QUserEntity.userEntity;
        queryFactory.update(user).set(user.verifyCount, 0L).execute();
    }
}
