package swp490.spa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swp490.spa.entities.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
    @Query("FROM Staff s WHERE s.user.id = ?1")
    Staff findByUserId(Integer userId);
}
