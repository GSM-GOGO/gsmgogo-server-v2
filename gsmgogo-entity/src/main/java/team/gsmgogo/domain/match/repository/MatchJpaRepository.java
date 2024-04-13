package team.gsmgogo.domain.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team.gsmgogo.domain.match.entity.MatchEntity;

public interface MatchJpaRepository extends JpaRepository<MatchEntity, Long> {}