package team.gsmgogo.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import team.gsmgogo.domain.auth.entity.BlackListRedisEntity;

import java.util.Optional;

public interface BlackListJpaRepository extends CrudRepository<BlackListRedisEntity, Long> {
    Optional<BlackListRedisEntity> findByAccessToken(String accessToken);
}
