package org.pharmacy.controller;

import lombok.RequiredArgsConstructor;
import org.pharmacy.dto.PharmacyChainRs;
import org.pharmacy.dto.CreatePharmacyChainRq;
import org.pharmacy.service.PharmacyChainService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для управления аптечными сетями.
 */
@RestController
@RequiredArgsConstructor
public class PharmacyChainControllerImpl implements PharmacyChainController {

    private final PharmacyChainService pharmacyChainService;

    @Override
    public PharmacyChainRs create(CreatePharmacyChainRq pharmacyChainRequest) {
        return pharmacyChainService.create(pharmacyChainRequest);
    }

    @Override
    public PharmacyChainRs findById(UUID id) {
        return pharmacyChainService.findById(id);
    }

    @Override
    public List<PharmacyChainRs> findAll() {
        return pharmacyChainService.findAll();
    }

    @Override
    public PharmacyChainRs update(UUID id, CreatePharmacyChainRq pharmacyChainRq) {
        return pharmacyChainService.update(id, pharmacyChainRq);
    }

    @Override
    public void deleteById(UUID id) {
        pharmacyChainService.deleteById(id);
    }
}
