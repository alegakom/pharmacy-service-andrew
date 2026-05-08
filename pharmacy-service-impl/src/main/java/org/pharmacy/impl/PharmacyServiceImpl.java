package org.pharmacy.impl;

import lombok.RequiredArgsConstructor;
import org.pharmacy.dto.PharmacyDto;
import org.pharmacy.entity.Pharmacy;
import org.pharmacy.entity.PharmacyChain;
import org.pharmacy.mapper.PharmacyMapper;
import org.pharmacy.repository.PharmacyChainRepository;
import org.pharmacy.repository.PharmacyRepository;
import org.pharmacy.request.CreatePharmacyRequest;
import org.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Реализация сервиса для аптеки.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final PharmacyChainRepository pharmacyChainRepository;
    private final PharmacyMapper pharmacyMapper;

    @Override
    @Transactional
    public PharmacyDto create(CreatePharmacyRequest pharmacyRequest) {
        Pharmacy pharmacy = pharmacyMapper.toEntity(pharmacyRequest);
        if (pharmacyRequest.getPharmacyChainId() != null) {
            PharmacyChain pharmacyChain = pharmacyChainRepository.getReferenceById(pharmacyRequest.getPharmacyChainId());
            pharmacy.setPharmacyChain(pharmacyChain);
        }
        Pharmacy saved = pharmacyRepository.save(pharmacy);
        return pharmacyMapper.toDto(saved);
    }

    @Override
    public PharmacyDto findById(UUID id) {
        return pharmacyRepository.findById(id)
                .map(pharmacyMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Pharmacy not found: " + id));
    }

    @Override
    public List<PharmacyDto> findAllByPharmacyChainId(UUID pharmacyChainId) {
        return pharmacyRepository.findAllByPharmacyChainId(pharmacyChainId).stream()
                .map(pharmacyMapper::toDto)
                .toList();
    }

    @Override
    public List<PharmacyDto> findAll() {
        return pharmacyRepository.findAll().stream()
                .map(pharmacyMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!pharmacyRepository.existsById(id)) {
            throw new NoSuchElementException("Pharmacy not found: " + id);
        }
        pharmacyRepository.deleteById(id);
    }
}
