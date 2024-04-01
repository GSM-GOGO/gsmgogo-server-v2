package team.gsmgogo.domain.match.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchEntity is a Querydsl query type for MatchEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchEntity extends EntityPathBase<MatchEntity> {

    private static final long serialVersionUID = 542835938L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchEntity matchEntity = new QMatchEntity("matchEntity");

    public final DateTimePath<java.time.LocalDateTime> endAt = createDateTime("endAt", java.time.LocalDateTime.class);

    public final BooleanPath isEnd = createBoolean("isEnd");

    public final NumberPath<Long> matchId = createNumber("matchId", Long.class);

    public final EnumPath<team.gsmgogo.domain.match.enums.MatchLevelType> matchLevel = createEnum("matchLevel", team.gsmgogo.domain.match.enums.MatchLevelType.class);

    public final EnumPath<team.gsmgogo.domain.team.enums.TeamType> matchType = createEnum("matchType", team.gsmgogo.domain.team.enums.TeamType.class);

    public final DateTimePath<java.time.LocalDateTime> startAt = createDateTime("startAt", java.time.LocalDateTime.class);

    public final team.gsmgogo.domain.team.entity.QTeamEntity teamA;

    public final NumberPath<Long> teamABet = createNumber("teamABet", Long.class);

    public final EnumPath<team.gsmgogo.domain.match.enums.TeamClassType> teamAClassType = createEnum("teamAClassType", team.gsmgogo.domain.match.enums.TeamClassType.class);

    public final EnumPath<team.gsmgogo.domain.user.enums.GradeEnum> teamAGrade = createEnum("teamAGrade", team.gsmgogo.domain.user.enums.GradeEnum.class);

    public final team.gsmgogo.domain.team.entity.QTeamEntity teamB;

    public final NumberPath<Long> teamBBet = createNumber("teamBBet", Long.class);

    public final EnumPath<team.gsmgogo.domain.match.enums.TeamClassType> teamBClassType = createEnum("teamBClassType", team.gsmgogo.domain.match.enums.TeamClassType.class);

    public final EnumPath<team.gsmgogo.domain.user.enums.GradeEnum> teamBGrade = createEnum("teamBGrade", team.gsmgogo.domain.user.enums.GradeEnum.class);

    public QMatchEntity(String variable) {
        this(MatchEntity.class, forVariable(variable), INITS);
    }

    public QMatchEntity(Path<? extends MatchEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchEntity(PathMetadata metadata, PathInits inits) {
        this(MatchEntity.class, metadata, inits);
    }

    public QMatchEntity(Class<? extends MatchEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.teamA = inits.isInitialized("teamA") ? new team.gsmgogo.domain.team.entity.QTeamEntity(forProperty("teamA"), inits.get("teamA")) : null;
        this.teamB = inits.isInitialized("teamB") ? new team.gsmgogo.domain.team.entity.QTeamEntity(forProperty("teamB"), inits.get("teamB")) : null;
    }

}

