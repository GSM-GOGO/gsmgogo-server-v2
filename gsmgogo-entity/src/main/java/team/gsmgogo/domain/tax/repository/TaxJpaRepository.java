package team.gsmgogo.domain.tax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gsmgogo.domain.tax.entity.TaxEntity;

public interface TaxJpaRepository extends JpaRepository<TaxEntity, Long> {
}
