package team.gsmgogo.domain.team.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.gsmgogo.domain.team.entity.QTeamEntity;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.teamparticipate.entity.QTeamParticipateEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<TeamEntity> findByTeamId(Long timeId) {
        QTeamEntity team = QTeamEntity.teamEntity;
        QTeamParticipateEntity participate = QTeamParticipateEntity.teamParticipateEntity;
        return jpaQueryFactory.selectFrom(team)
                .join(participate.team, team)
                .where(team.teamId.eq(timeId))
                .fetch();
    }

}
