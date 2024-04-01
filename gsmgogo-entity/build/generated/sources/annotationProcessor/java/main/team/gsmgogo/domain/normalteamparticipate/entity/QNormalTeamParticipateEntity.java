package team.gsmgogo.domain.normalteamparticipate.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNormalTeamParticipateEntity is a Querydsl query type for NormalTeamParticipateEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNormalTeamParticipateEntity extends EntityPathBase<NormalTeamParticipateEntity> {

    private static final long serialVersionUID = 726809794L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNormalTeamParticipateEntity normalTeamParticipateEntity = new QNormalTeamParticipateEntity("normalTeamParticipateEntity");

    public final NumberPath<Long> normalTeamParticipateId = createNumber("normalTeamParticipateId", Long.class);

    public final EnumPath<team.gsmgogo.domain.normalteamparticipate.enums.NormalTeamType> normalTeamType = createEnum("normalTeamType", team.gsmgogo.domain.normalteamparticipate.enums.NormalTeamType.class);

    public final team.gsmgogo.domain.team.entity.QTeamEntity team;

    public final team.gsmgogo.domain.user.entity.QUserEntity user;

    public QNormalTeamParticipateEntity(String variable) {
        this(NormalTeamParticipateEntity.class, forVariable(variable), INITS);
    }

    public QNormalTeamParticipateEntity(Path<? extends NormalTeamParticipateEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNormalTeamParticipateEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNormalTeamParticipateEntity(PathMetadata metadata, PathInits inits) {
        this(NormalTeamParticipateEntity.class, metadata, inits);
    }

    public QNormalTeamParticipateEntity(Class<? extends NormalTeamParticipateEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new team.gsmgogo.domain.team.entity.QTeamEntity(forProperty("team"), inits.get("team")) : null;
        this.user = inits.isInitialized("user") ? new team.gsmgogo.domain.user.entity.QUserEntity(forProperty("user")) : null;
    }

}

