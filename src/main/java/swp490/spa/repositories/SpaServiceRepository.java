package swp490.spa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swp490.spa.entities.SpaService;
import swp490.spa.entities.Status;

public interface SpaServiceRepository extends JpaRepository<SpaService, Integer> {
    @Query("FROM SpaService s where s.spa.id = ?1 and s.status = ?2")
    Page<SpaService> findBySpaIdAndStatus(Integer spaId, Status status, Pageable pageable);
}
