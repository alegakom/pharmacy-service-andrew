package org.pharmacy.impl;

import lombok.RequiredArgsConstructor;
import org.pharmacy.dto.PharmacyChainDto;
import org.pharmacy.entity.PharmacyChain;
import org.pharmacy.mapper.PharmacyChainMapper;
import org.pharmacy.repository.PharmacyChainRepository;
import org.pharmacy.request.CreatePharmacyChainRequest;
import org.pharmacy.service.PharmacyChainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Реализация сервиса для аптечной сети.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PharmacyChainServiceImpl implements PharmacyChainService {

    private final PharmacyChainRepository pharmacyChainRepository;
    private final PharmacyChainMapper pharmacyChainMapper;

    @Override
    @Transactional
    public PharmacyChainDto create(CreatePharmacyChainRequest pharmacyChainRequest) {
        PharmacyChain pharmacyChain = pharmacyChainMapper.toEntity(pharmacyChainRequest);
        PharmacyChain saved = pharmacyChainRepository.save(pharmacyChain);
        return pharmacyChainMapper.toDto(saved);
    }

    @Override
    public PharmacyChainDto findById(UUID id) {
        return pharmacyChainRepository.findById(id)
                .map(pharmacyChainMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("PharmacyChain not found: " + id));
    }

    @Override
    public List<PharmacyChainDto> findAll() {
        return pharmacyChainRepository.findAll()
                .stream()
                .map(pharmacyChainMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!pharmacyChainRepository.existsById(id)) {
            throw new NoSuchElementException("PharmacyChain not found: " + id);
        }
        pharmacyChainRepository.deleteById(id);
    }
}
