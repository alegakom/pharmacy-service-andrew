package org.pharmacy.service;

import org.pharmacy.dto.PharmacyChainDto;
import org.pharmacy.request.CreatePharmacyChainRequest;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса для аптечной сети.
 */
public interface PharmacyChainService {

    PharmacyChainDto create(CreatePharmacyChainRequest pharmacyChainRequest);

    PharmacyChainDto findById(UUID id);

    List<PharmacyChainDto> findAll();

    void deleteById(UUID id);
}
