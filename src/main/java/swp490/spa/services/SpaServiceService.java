package swp490.spa.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.spa.entities.SpaService;
import swp490.spa.entities.Status;
import swp490.spa.repositories.SpaServiceRepository;

@Service
public class SpaServiceService {
    private SpaServiceRepository spaServiceRepository;

    public SpaServiceService(SpaServiceRepository spaServiceRepository) {
        this.spaServiceRepository = spaServiceRepository;
    }

    public Page<SpaService> findBySpaIdAndStatus(Integer spaId, Status status, Pageable pageable){
        return this.spaServiceRepository.findBySpaIdAndStatus(spaId, status, pageable);
    }
}
