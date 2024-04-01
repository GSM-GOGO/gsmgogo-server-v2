package team.gsmgogo.domain.team.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamEntity is a Querydsl query type for TeamEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamEntity extends EntityPathBase<TeamEntity> {

    private static final long serialVersionUID = -1549400298L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamEntity teamEntity = new QTeamEntity("teamEntity");

    public final team.gsmgogo.domain.user.entity.QUserEntity author;

    public final EnumPath<team.gsmgogo.domain.team.enums.BadmintonRank> badmintonRank = createEnum("badmintonRank", team.gsmgogo.domain.team.enums.BadmintonRank.class);

    public final ListPath<team.gsmgogo.domain.follow.entity.FollowEntity, team.gsmgogo.domain.follow.entity.QFollowEntity> follows = this.<team.gsmgogo.domain.follow.entity.FollowEntity, team.gsmgogo.domain.follow.entity.QFollowEntity>createList("follows", team.gsmgogo.domain.follow.entity.FollowEntity.class, team.gsmgogo.domain.follow.entity.QFollowEntity.class, PathInits.DIRECT2);

    public final BooleanPath isSurvived = createBoolean("isSurvived");

    public final ListPath<team.gsmgogo.domain.normalteamparticipate.entity.NormalTeamParticipateEntity, team.gsmgogo.domain.normalteamparticipate.entity.QNormalTeamParticipateEntity> normalTeamParticipateEntities = this.<team.gsmgogo.domain.normalteamparticipate.entity.NormalTeamParticipateEntity, team.gsmgogo.domain.normalteamparticipate.entity.QNormalTeamParticipateEntity>createList("normalTeamParticipateEntities", team.gsmgogo.domain.normalteamparticipate.entity.NormalTeamParticipateEntity.class, team.gsmgogo.domain.normalteamparticipate.entity.QNormalTeamParticipateEntity.class, PathInits.DIRECT2);

    public final EnumPath<team.gsmgogo.domain.user.enums.ClassEnum> teamClass = createEnum("teamClass", team.gsmgogo.domain.user.enums.ClassEnum.class);

    public final EnumPath<team.gsmgogo.domain.user.enums.GradeEnum> teamGrade = createEnum("teamGrade", team.gsmgogo.domain.user.enums.GradeEnum.class);

    public final NumberPath<Long> teamId = createNumber("teamId", Long.class);

    public final StringPath teamName = createString("teamName");

    public final ListPath<team.gsmgogo.domain.teamparticipate.entity.TeamParticipateEntity, team.gsmgogo.domain.teamparticipate.entity.QTeamParticipateEntity> teamParticipates = this.<team.gsmgogo.domain.teamparticipate.entity.TeamParticipateEntity, team.gsmgogo.domain.teamparticipate.entity.QTeamParticipateEntity>createList("teamParticipates", team.gsmgogo.domain.teamparticipate.entity.TeamParticipateEntity.class, team.gsmgogo.domain.teamparticipate.entity.QTeamParticipateEntity.class, PathInits.DIRECT2);

    public final EnumPath<team.gsmgogo.domain.team.enums.TeamType> teamType = createEnum("teamType", team.gsmgogo.domain.team.enums.TeamType.class);

    public final NumberPath<Integer> winCount = createNumber("winCount", Integer.class);

    public QTeamEntity(String variable) {
        this(TeamEntity.class, forVariable(variable), INITS);
    }

    public QTeamEntity(Path<? extends TeamEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamEntity(PathMetadata metadata, PathInits inits) {
        this(TeamEntity.class, metadata, inits);
    }

    public QTeamEntity(Class<? extends TeamEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new team.gsmgogo.domain.user.entity.QUserEntity(forProperty("author")) : null;
    }

}

