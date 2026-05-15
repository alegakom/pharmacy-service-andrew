package org.pharmacy.service;

import org.pharmacy.dto.PharmacyChainRs;
import org.pharmacy.dto.CreatePharmacyChainRq;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса для аптечной сети.
 */
public interface PharmacyChainService {

    PharmacyChainRs create(CreatePharmacyChainRq pharmacyChainRq);

    PharmacyChainRs findById(UUID id);

    List<PharmacyChainRs> findAll();

    PharmacyChainRs update(UUID id, CreatePharmacyChainRq pharmacyChainRq);

    void deleteById(UUID id);
}
