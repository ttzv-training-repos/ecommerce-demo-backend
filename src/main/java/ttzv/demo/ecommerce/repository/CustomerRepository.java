package ttzv.demo.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ttzv.demo.ecommerce.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
