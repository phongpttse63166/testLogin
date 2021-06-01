package swp490.spa.services;

import org.springframework.stereotype.Service;
import swp490.spa.entities.Staff;
import swp490.spa.repositories.StaffRepository;

@Service
public class StaffService {
    private StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Staff findByStaffId(Integer userId){
        return this.staffRepository.findByUserId(userId);
    }
}
