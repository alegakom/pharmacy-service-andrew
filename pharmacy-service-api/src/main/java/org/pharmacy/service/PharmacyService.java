package org.pharmacy.service;

import org.pharmacy.dto.PharmacyDto;
import org.pharmacy.request.CreatePharmacyRequest;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса для аптеки.
 */
public interface PharmacyService {

    PharmacyDto create(CreatePharmacyRequest pharmacyRequest);

    PharmacyDto findById(UUID id);

    List<PharmacyDto> findAllByPharmacyChainId(UUID pharmacyChainId);

    List<PharmacyDto> findAll();

    void deleteById(UUID id);

}
