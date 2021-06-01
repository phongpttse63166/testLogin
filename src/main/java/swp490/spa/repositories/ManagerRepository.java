package swp490.spa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swp490.spa.entities.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    @Query("FROM Manager m WHERE m.user.id = ?1")
    Manager findByUserId(Integer userId);
}
