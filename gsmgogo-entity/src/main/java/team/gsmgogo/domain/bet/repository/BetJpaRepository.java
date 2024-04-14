package team.gsmgogo.domain.bet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import team.gsmgogo.domain.bet.entity.BetEntity;
import team.gsmgogo.domain.match.entity.MatchEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BetJpaRepository extends JpaRepository<BetEntity, Long> {
    Page<BetEntity> findByMatch(MatchEntity match, Pageable pageable);
}
