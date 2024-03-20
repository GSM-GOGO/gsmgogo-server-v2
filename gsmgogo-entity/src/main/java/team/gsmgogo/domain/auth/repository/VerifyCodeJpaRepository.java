package team.gsmgogo.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import team.gsmgogo.domain.auth.entity.VerifyCodeRedisEntity;

import java.util.Optional;

public interface VerifyCodeJpaRepository extends CrudRepository<VerifyCodeRedisEntity, Long> {
    Optional<VerifyCodeRedisEntity> findByUserId(Long userId);
}
