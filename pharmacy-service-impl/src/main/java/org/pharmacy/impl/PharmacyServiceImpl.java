package org.pharmacy.impl;

import lombok.RequiredArgsConstructor;
import org.pharmacy.constant.ExceptionMessageConstants;
import org.pharmacy.dto.PharmacyRs;
import org.pharmacy.entity.Pharmacy;
import org.pharmacy.entity.PharmacyChain;
import org.pharmacy.exception.DuplicateInnException;
import org.pharmacy.exception.NotFoundCrmException;
import org.pharmacy.mapper.PharmacyMapper;
import org.pharmacy.repository.PharmacyChainRepository;
import org.pharmacy.repository.PharmacyRepository;
import org.pharmacy.dto.CreatePharmacyRq;
import org.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Реализация сервиса для аптеки.
 */
@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final PharmacyChainRepository pharmacyChainRepository;
    private final PharmacyMapper pharmacyMapper;

    @Override
    @Transactional
    public PharmacyRs create(CreatePharmacyRq pharmacyRq) {
        if (pharmacyRepository.existsByInn(pharmacyRq.getInn())) {
            throw new DuplicateInnException(pharmacyRq.getInn());
        }

        Pharmacy pharmacy = pharmacyMapper.toEntity(pharmacyRq);
        if (pharmacyRq.getPharmacyChainId() != null) {
            PharmacyChain pharmacyChain = pharmacyChainRepository.getReferenceById(pharmacyRq.getPharmacyChainId());
            pharmacy.setPharmacyChain(pharmacyChain);
        }

        Pharmacy saved = pharmacyRepository.save(pharmacy);
        return pharmacyMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PharmacyRs findById(UUID id) {
        return pharmacyRepository.findById(id)
                .map(pharmacyMapper::toDto)
                .orElseThrow(() -> new NotFoundCrmException(
                        String.format(ExceptionMessageConstants.PHARMACY_NOT_FOUND, id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PharmacyRs> findAllByPharmacyChainId(UUID pharmacyChainId) {
        return pharmacyRepository.findAllByPharmacyChainId(pharmacyChainId).stream()
                .map(pharmacyMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PharmacyRs> findAllByPharmacyChainIsNull() {
        return pharmacyRepository.findAllByPharmacyChainIsNull().stream()
                .map(pharmacyMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PharmacyRs> findAll() {
        return pharmacyRepository.findAll().stream()
                .map(pharmacyMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public PharmacyRs update(UUID id, CreatePharmacyRq pharmacyRq) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new NotFoundCrmException(
                        String.format(ExceptionMessageConstants.PHARMACY_NOT_FOUND, id)));

        String newInn = pharmacyRq.getInn();
        if (!pharmacy.getInn().equals(newInn) && pharmacyRepository.existsByInn(newInn)) {
            throw new DuplicateInnException(newInn);
        }

        pharmacyMapper.updateFromDto(pharmacyRq, pharmacy);
        pharmacyRepository.save(pharmacy);
        return pharmacyMapper.toDto(pharmacy);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!pharmacyRepository.existsById(id)) {
            throw new NotFoundCrmException(
                    String.format(ExceptionMessageConstants.PHARMACY_NOT_FOUND, id));
        }
        pharmacyRepository.deleteById(id);
    }
}
