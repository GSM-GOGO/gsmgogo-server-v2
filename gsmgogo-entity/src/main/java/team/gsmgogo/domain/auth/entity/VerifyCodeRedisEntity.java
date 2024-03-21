package team.gsmgogo.domain.auth.entity;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("verifyCode")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VerifyCodeRedisEntity {
    @Id
    @Indexed
    @Column(name = "user_id")
    private Long userId;

    @Indexed
    @Column(name = "code")
    private String code;

    @TimeToLive
    @Column(name = "expired_at")
    private Long expiredAt;
}
