package team.gsmgogo.domain.teamparticipate.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamParticipateEntity is a Querydsl query type for TeamParticipateEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamParticipateEntity extends EntityPathBase<TeamParticipateEntity> {

    private static final long serialVersionUID = -342715966L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamParticipateEntity teamParticipateEntity = new QTeamParticipateEntity("teamParticipateEntity");

    public final NumberPath<Double> positionX = createNumber("positionX", Double.class);

    public final NumberPath<Double> positionY = createNumber("positionY", Double.class);

    public final team.gsmgogo.domain.team.entity.QTeamEntity team;

    public final NumberPath<Long> teamParticipateId = createNumber("teamParticipateId", Long.class);

    public final team.gsmgogo.domain.user.entity.QUserEntity user;

    public QTeamParticipateEntity(String variable) {
        this(TeamParticipateEntity.class, forVariable(variable), INITS);
    }

    public QTeamParticipateEntity(Path<? extends TeamParticipateEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamParticipateEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamParticipateEntity(PathMetadata metadata, PathInits inits) {
        this(TeamParticipateEntity.class, metadata, inits);
    }

    public QTeamParticipateEntity(Class<? extends TeamParticipateEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new team.gsmgogo.domain.team.entity.QTeamEntity(forProperty("team"), inits.get("team")) : null;
        this.user = inits.isInitialized("user") ? new team.gsmgogo.domain.user.entity.QUserEntity(forProperty("user")) : null;
    }

}

