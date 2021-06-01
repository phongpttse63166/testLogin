package swp490.spa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swp490.spa.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("FROM Customer c where c.user.id = ?1")
    Customer findByUserId(Integer userId);
}
