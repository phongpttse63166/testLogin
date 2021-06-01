package swp490.spa.services;

import org.springframework.stereotype.Service;
import swp490.spa.entities.Manager;
import swp490.spa.repositories.ManagerRepository;

@Service
public class ManagerService {
    private ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public Manager findManagerById(Integer userId){
        return this.managerRepository.findByUserId(userId);
    }
}
