package team.gsmgogo.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import team.gsmgogo.domain.auth.entity.RefreshTokenRedisEntity;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends CrudRepository<RefreshTokenRedisEntity, String> {
    Optional<RefreshTokenRedisEntity> findByUserEmail(String email);
}
