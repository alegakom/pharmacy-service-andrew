package org.pharmacy.impl;

import lombok.RequiredArgsConstructor;
import org.pharmacy.constant.ExceptionMessageConstants;
import org.pharmacy.dto.CreatePharmacyChainRq;
import org.pharmacy.dto.PharmacyChainRs;
import org.pharmacy.entity.PharmacyChain;
import org.pharmacy.exception.DuplicateDataException;
import org.pharmacy.exception.NotFoundCrmException;
import org.pharmacy.mapper.PharmacyChainMapper;
import org.pharmacy.repository.PharmacyChainRepository;
import org.pharmacy.service.PharmacyChainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        if (pharmacyChainRepository.existsByInn(pharmacyChainRq.getInn())) {
            throw new DuplicateDataException(pharmacyChainRq.getInn());
        }

        PharmacyChain pharmacyChain = pharmacyChainMapper.toEntity(pharmacyChainRq);
        PharmacyChain saved = pharmacyChainRepository.save(pharmacyChain);
        return pharmacyChainMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PharmacyChainRs findById(UUID id) {
        return pharmacyChainRepository.findById(id)
                .map(pharmacyChainMapper::toDto)
                .orElseThrow(() -> new NotFoundCrmException(
                        String.format(ExceptionMessageConstants.PHARMACY_CHAIN_NOT_FOUND, id)));
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
                .orElseThrow(() -> new NotFoundCrmException(
                        String.format(ExceptionMessageConstants.PHARMACY_CHAIN_NOT_FOUND, id)));

        String newInn = pharmacyChainRq.getInn();
        if (!pharmacyChain.getInn().equals(newInn) && pharmacyChainRepository.existsByInn(newInn)) {
            throw new DuplicateDataException(newInn);
        }

        pharmacyChainMapper.updateFromDto(pharmacyChainRq, pharmacyChain);
        pharmacyChainRepository.save(pharmacyChain);
        return pharmacyChainMapper.toDto(pharmacyChain);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!pharmacyChainRepository.existsById(id)) {
            throw new NotFoundCrmException(
                    String.format(ExceptionMessageConstants.PHARMACY_CHAIN_NOT_FOUND, id));
        }
        pharmacyChainRepository.deleteById(id);
    }
}
