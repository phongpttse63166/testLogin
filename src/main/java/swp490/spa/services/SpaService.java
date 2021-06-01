package swp490.spa.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.spa.entities.Spa;
import swp490.spa.entities.Status;
import swp490.spa.repositories.SpaRepository;

@Service
public class SpaService {
    private SpaRepository spaRepository;

    public SpaService(SpaRepository spaRepository) {
        this.spaRepository = spaRepository;
    }

    public Page<Spa> findByStatus(Status status, Pageable pageable){
        return this.spaRepository.findByStatus(status, pageable);
    }
}
