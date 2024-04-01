package team.gsmgogo.domain.matchresult.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchResultEntity is a Querydsl query type for MatchResultEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchResultEntity extends EntityPathBase<MatchResultEntity> {

    private static final long serialVersionUID = -1330804382L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchResultEntity matchResultEntity = new QMatchResultEntity("matchResultEntity");

    public final team.gsmgogo.domain.match.entity.QMatchEntity match;

    public final NumberPath<Long> matchResultId = createNumber("matchResultId", Long.class);

    public final team.gsmgogo.domain.team.entity.QTeamEntity team;

    public final NumberPath<Long> teamABet = createNumber("teamABet", Long.class);

    public final NumberPath<Integer> teamAScore = createNumber("teamAScore", Integer.class);

    public final NumberPath<Long> teamBBet = createNumber("teamBBet", Long.class);

    public final NumberPath<Integer> teamBScore = createNumber("teamBScore", Integer.class);

    public QMatchResultEntity(String variable) {
        this(MatchResultEntity.class, forVariable(variable), INITS);
    }

    public QMatchResultEntity(Path<? extends MatchResultEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchResultEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchResultEntity(PathMetadata metadata, PathInits inits) {
        this(MatchResultEntity.class, metadata, inits);
    }

    public QMatchResultEntity(Class<? extends MatchResultEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.match = inits.isInitialized("match") ? new team.gsmgogo.domain.match.entity.QMatchEntity(forProperty("match"), inits.get("match")) : null;
        this.team = inits.isInitialized("team") ? new team.gsmgogo.domain.team.entity.QTeamEntity(forProperty("team"), inits.get("team")) : null;
    }

}

