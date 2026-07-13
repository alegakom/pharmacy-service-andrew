package org.pharmacy.service;

import org.pharmacy.dto.PharmacyRs;
import org.pharmacy.dto.PharmacyInfoDto;
import org.pharmacy.dto.CreatePharmacyRq;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса для аптеки.
 */
public interface PharmacyService {

    PharmacyRs create(CreatePharmacyRq pharmacyRq);

    PharmacyRs findById(UUID id);

    List<PharmacyRs> findAllByPharmacyChainId(UUID pharmacyChainId);

    List<PharmacyRs> findAll();

    PharmacyRs update(UUID id, CreatePharmacyRq pharmacyRq);

    void deleteById(UUID id);

    PharmacyInfoDto getPharmacyInfo(UUID pharmacyId);

    void checkingToken(List<UUID> pharmacyIds);
}
