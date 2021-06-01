package swp490.spa.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.spa.entities.Spa;
import swp490.spa.entities.SpaAddress;
import swp490.spa.repositories.SpaAddressRepository;

@Service
public class SpaAddressService {
    private SpaAddressRepository spaAddressRepository;

    public SpaAddressService(SpaAddressRepository spaAddressRepository) {
        this.spaAddressRepository = spaAddressRepository;
    }

    public Page<SpaAddress> findBySpaId(Integer spaId, Pageable pageable){
        return this.spaAddressRepository.findBySpaId(spaId, pageable);
    }


}
