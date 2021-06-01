package swp490.spa.services;

import org.springframework.stereotype.Service;
import swp490.spa.entities.Customer;
import swp490.spa.repositories.CustomerRepository;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findByUserId(Integer userId){
        return customerRepository.findByUserId(userId);
    }
}
