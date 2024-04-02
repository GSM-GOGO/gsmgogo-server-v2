package team.gsmgogo.domain.team.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.gsmgogo.domain.team.entity.TeamEntity;

import static team.gsmgogo.domain.team.entity.QTeamEntity.teamEntity;
import static team.gsmgogo.domain.teamparticipate.entity.QTeamParticipateEntity.teamParticipateEntity;
import static team.gsmgogo.domain.user.entity.QUserEntity.userEntity;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeamQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<TeamEntity> findByTeamId(Long timeId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(teamEntity)
                .join(teamEntity.teamParticipates, teamParticipateEntity).fetchJoin()
                .join(teamParticipateEntity.user, userEntity).fetchJoin()
                .where(teamEntity.teamId.eq(timeId))
                .fetchOne());
    }

}
