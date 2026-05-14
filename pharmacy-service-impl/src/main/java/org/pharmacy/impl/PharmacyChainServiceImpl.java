package org.pharmacy.impl;

import lombok.RequiredArgsConstructor;
import org.pharmacy.constant.ExceptionMessageConstants;
import org.pharmacy.dto.CreatePharmacyChainRq;
import org.pharmacy.dto.PharmacyChainRs;
import org.pharmacy.entity.PharmacyChain;
import org.pharmacy.mapper.PharmacyChainMapper;
import org.pharmacy.repository.PharmacyChainRepository;
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
public class PharmacyChainServiceImpl implements PharmacyChainService {

    private final PharmacyChainRepository pharmacyChainRepository;
    private final PharmacyChainMapper pharmacyChainMapper;

    @Override
    @Transactional
    public PharmacyChainRs create(CreatePharmacyChainRq pharmacyChainRq) {
        PharmacyChain pharmacyChain = pharmacyChainMapper.toEntity(pharmacyChainRq);
        PharmacyChain saved = pharmacyChainRepository.save(pharmacyChain);
        return pharmacyChainMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PharmacyChainRs findById(UUID id) {
        return pharmacyChainRepository.findById(id)
                .map(pharmacyChainMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(ExceptionMessageConstants.PHARMACY_CHAIN_NOT_FOUND + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PharmacyChainRs> findAll() {
        return pharmacyChainRepository.findAll()
                .stream()
                .map(pharmacyChainMapper::toDto)
                .toList();
    }

    @Override
    public PharmacyChainRs update(UUID id, CreatePharmacyChainRq pharmacyChainRq) {
        PharmacyChain pharmacyChain = pharmacyChainRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ExceptionMessageConstants.PHARMACY_CHAIN_NOT_FOUND + id));
        pharmacyChainMapper.updateFromDto(pharmacyChainRq, pharmacyChain);

        pharmacyChainRepository.save(pharmacyChain);
        return pharmacyChainMapper.toDto(pharmacyChain);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!pharmacyChainRepository.existsById(id)) {
            throw new NoSuchElementException(ExceptionMessageConstants.PHARMACY_CHAIN_NOT_FOUND + id);
        }
        pharmacyChainRepository.deleteById(id);
    }
}
