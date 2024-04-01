package team.gsmgogo.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 896520178L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final ListPath<team.gsmgogo.domain.follow.entity.FollowEntity, team.gsmgogo.domain.follow.entity.QFollowEntity> follows = this.<team.gsmgogo.domain.follow.entity.FollowEntity, team.gsmgogo.domain.follow.entity.QFollowEntity>createList("follows", team.gsmgogo.domain.follow.entity.FollowEntity.class, team.gsmgogo.domain.follow.entity.QFollowEntity.class, PathInits.DIRECT2);

    public final EnumPath<team.gsmgogo.domain.user.enums.Gender> gender = createEnum("gender", team.gsmgogo.domain.user.enums.Gender.class);

    public final EnumPath<team.gsmgogo.domain.user.enums.IsVerify> isVerify = createEnum("isVerify", team.gsmgogo.domain.user.enums.IsVerify.class);

    public final ListPath<team.gsmgogo.domain.normalteamparticipate.entity.NormalTeamParticipateEntity, team.gsmgogo.domain.normalteamparticipate.entity.QNormalTeamParticipateEntity> normalTeamParticipateEntities = this.<team.gsmgogo.domain.normalteamparticipate.entity.NormalTeamParticipateEntity, team.gsmgogo.domain.normalteamparticipate.entity.QNormalTeamParticipateEntity>createList("normalTeamParticipateEntities", team.gsmgogo.domain.normalteamparticipate.entity.NormalTeamParticipateEntity.class, team.gsmgogo.domain.normalteamparticipate.entity.QNormalTeamParticipateEntity.class, PathInits.DIRECT2);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final EnumPath<team.gsmgogo.domain.user.enums.Role> role = createEnum("role", team.gsmgogo.domain.user.enums.Role.class);

    public final EnumPath<team.gsmgogo.domain.user.enums.SchoolRole> schoolRole = createEnum("schoolRole", team.gsmgogo.domain.user.enums.SchoolRole.class);

    public final ListPath<team.gsmgogo.domain.teamparticipate.entity.TeamParticipateEntity, team.gsmgogo.domain.teamparticipate.entity.QTeamParticipateEntity> teamParticipates = this.<team.gsmgogo.domain.teamparticipate.entity.TeamParticipateEntity, team.gsmgogo.domain.teamparticipate.entity.QTeamParticipateEntity>createList("teamParticipates", team.gsmgogo.domain.teamparticipate.entity.TeamParticipateEntity.class, team.gsmgogo.domain.teamparticipate.entity.QTeamParticipateEntity.class, PathInits.DIRECT2);

    public final EnumPath<team.gsmgogo.domain.user.enums.ClassEnum> userClass = createEnum("userClass", team.gsmgogo.domain.user.enums.ClassEnum.class);

    public final StringPath userEmail = createString("userEmail");

    public final EnumPath<team.gsmgogo.domain.user.enums.GradeEnum> userGrade = createEnum("userGrade", team.gsmgogo.domain.user.enums.GradeEnum.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userName = createString("userName");

    public final NumberPath<Integer> userNum = createNumber("userNum", Integer.class);

    public final NumberPath<Long> verifyCount = createNumber("verifyCount", Long.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

