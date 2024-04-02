package team.gsmgogo.domain.team.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.gsmgogo.domain.team.entity.QTeamEntity;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.teamparticipate.entity.QTeamParticipateEntity;
import team.gsmgogo.domain.user.entity.QUserEntity;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeamQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<TeamEntity> findByTeamId(Long timeId) {
        QTeamEntity team = QTeamEntity.teamEntity;
        QTeamParticipateEntity participate = QTeamParticipateEntity.teamParticipateEntity;
        QUserEntity user = QUserEntity.userEntity;

        return Optional.ofNullable(jpaQueryFactory.selectFrom(team)
                .join(team.teamParticipates, participate).fetchJoin()
                .join(participate.user, user).fetchJoin()
                .where(team.teamId.eq(timeId))
                .fetchOne());
    }

}
