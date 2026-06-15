package org.pharmacy.controller;

import lombok.RequiredArgsConstructor;
import org.pharmacy.dto.PharmacyInfoDto;
import org.pharmacy.dto.PharmacyRs;
import org.pharmacy.dto.CreatePharmacyRq;
import org.pharmacy.service.PharmacyService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для управления аптеками.
 */
@RestController
@RequiredArgsConstructor
public class PharmacyControllerImpl implements PharmacyController {

    private final PharmacyService pharmacyService;

    @Override
    public PharmacyRs create(CreatePharmacyRq pharmacyRequest) {
        return pharmacyService.create(pharmacyRequest);
    }

    @Override
    public PharmacyRs findById(UUID id) {
        return pharmacyService.findById(id);
    }

    @Override
    public List<PharmacyRs> findAll(UUID chainId) {
        if (chainId != null) {
            return pharmacyService.findAllByPharmacyChainId(chainId);
        }

        return pharmacyService.findAll();
    }

    @Override
    public PharmacyRs update(UUID id, CreatePharmacyRq pharmacyRq) {
        return pharmacyService.update(id, pharmacyRq);
    }

    @Override
    public void deleteById(UUID id) {
        pharmacyService.deleteById(id);
    }

    @Override
    public PharmacyInfoDto getPharmacyInfo(UUID pharmacyId) {
        return pharmacyService.getPharmacyInfo(pharmacyId);
    }
}
