package team.gsmgogo.domain.bet.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBetEntity is a Querydsl query type for BetEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBetEntity extends EntityPathBase<BetEntity> {

    private static final long serialVersionUID = -1371792670L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBetEntity betEntity = new QBetEntity("betEntity");

    public final NumberPath<Long> betId = createNumber("betId", Long.class);

    public final NumberPath<Long> betPoint = createNumber("betPoint", Long.class);

    public final NumberPath<Integer> betScoreA = createNumber("betScoreA", Integer.class);

    public final NumberPath<Integer> betScoreB = createNumber("betScoreB", Integer.class);

    public final team.gsmgogo.domain.match.entity.QMatchEntity match;

    public final team.gsmgogo.domain.team.entity.QTeamEntity team;

    public final team.gsmgogo.domain.user.entity.QUserEntity user;

    public QBetEntity(String variable) {
        this(BetEntity.class, forVariable(variable), INITS);
    }

    public QBetEntity(Path<? extends BetEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBetEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBetEntity(PathMetadata metadata, PathInits inits) {
        this(BetEntity.class, metadata, inits);
    }

    public QBetEntity(Class<? extends BetEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.match = inits.isInitialized("match") ? new team.gsmgogo.domain.match.entity.QMatchEntity(forProperty("match"), inits.get("match")) : null;
        this.team = inits.isInitialized("team") ? new team.gsmgogo.domain.team.entity.QTeamEntity(forProperty("team"), inits.get("team")) : null;
        this.user = inits.isInitialized("user") ? new team.gsmgogo.domain.user.entity.QUserEntity(forProperty("user")) : null;
    }

}

