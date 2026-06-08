package org.pharmacy.service;

import lombok.RequiredArgsConstructor;
import org.pharmacy.client.AuthClient;
import org.pharmacy.client.PharmacyInfoClient;
import org.pharmacy.constant.ExceptionMessageConstants;
import org.pharmacy.constant.PharmacyChainConstants;
import org.pharmacy.dto.PharmacyInfoDto;
import org.pharmacy.dto.PharmacyRs;
import org.pharmacy.dto.TokenInfoDto;
import org.pharmacy.entity.Pharmacy;
import org.pharmacy.entity.PharmacyChain;
import org.pharmacy.exception.DuplicateDataException;
import org.pharmacy.exception.NotFoundCrmException;
import org.pharmacy.mapper.PharmacyMapper;
import org.pharmacy.repository.PharmacyChainRepository;
import org.pharmacy.repository.PharmacyRepository;
import org.pharmacy.dto.CreatePharmacyRq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final AuthClient authClient;
    private final PharmacyInfoClient pharmacyInfoClient;

    @Override
    @Transactional
    public PharmacyRs create(CreatePharmacyRq pharmacyRq) {
        if (pharmacyRepository.existsByInn(pharmacyRq.getInn())) {
            throw new DuplicateDataException(pharmacyRq.getInn());
        }

        PharmacyChain pharmacyChain;
        UUID pharmacyChainId = pharmacyRq.getPharmacyChainId();
        if (pharmacyChainId != null) {
            pharmacyChain = pharmacyChainRepository.findById(pharmacyChainId)
                    .orElseThrow(() -> new NotFoundCrmException(
                            String.format(ExceptionMessageConstants.PHARMACY_CHAIN_NOT_FOUND, pharmacyChainId)));
        } else {
            pharmacyChain = pharmacyChainRepository.getReferenceById(PharmacyChainConstants.WITHOUT_CHAIN_DEFAULT_ID);
        }

        Pharmacy pharmacy = pharmacyMapper.convertToEntity(pharmacyRq);
        pharmacy.setPharmacyChain(pharmacyChain);

        Pharmacy saved = pharmacyRepository.save(pharmacy);
        return pharmacyMapper.convertToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PharmacyRs findById(UUID id) {
        return pharmacyRepository.findById(id)
                .map(pharmacyMapper::convertToDto)
                .orElseThrow(() -> new NotFoundCrmException(
                        String.format(ExceptionMessageConstants.PHARMACY_NOT_FOUND, id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PharmacyRs> findAllByPharmacyChainId(UUID pharmacyChainId) {
        return pharmacyRepository.findAllByPharmacyChainId(pharmacyChainId).stream()
                .map(pharmacyMapper::convertToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PharmacyRs> findAll() {
        return pharmacyRepository.findAll().stream()
                .map(pharmacyMapper::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public PharmacyRs update(UUID id, CreatePharmacyRq pharmacyRq) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new NotFoundCrmException(
                        String.format(ExceptionMessageConstants.PHARMACY_NOT_FOUND, id)));

        String newInn = pharmacyRq.getInn();
        if (newInn != null && !pharmacy.getInn().equals(newInn) && pharmacyRepository.existsByInn(newInn)) {
            throw new DuplicateDataException(newInn);
        }

        UUID newPharmacyChainId = pharmacyRq.getPharmacyChainId();
        if (newPharmacyChainId != null) {
            PharmacyChain newPharmacyChain = pharmacyChainRepository.findById(newPharmacyChainId)
                    .orElseThrow(() -> new NotFoundCrmException(
                            String.format(ExceptionMessageConstants.PHARMACY_CHAIN_NOT_FOUND, newPharmacyChainId)));
            pharmacy.setPharmacyChain(newPharmacyChain);
        }

        pharmacyMapper.updateEntityFromDto(pharmacyRq, pharmacy);
        pharmacyRepository.save(pharmacy);
        return pharmacyMapper.convertToDto(pharmacy);
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

    @Override
    @Transactional
    public PharmacyInfoDto getPharmacyInfo(UUID pharmacyId) {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new NotFoundCrmException(
                        String.format(ExceptionMessageConstants.PHARMACY_NOT_FOUND, pharmacyId)));

        String token = pharmacy.getToken();
        LocalDate expiredDate = pharmacy.getExpiredDate();
        if (token == null || expiredDate == null || expiredDate.isBefore(LocalDate.now())) {
            TokenInfoDto tokenInfo = authClient.getToken(pharmacyId);
            token = tokenInfo.getToken();
            pharmacy.setToken(token);
            pharmacy.setExpiredDate(tokenInfo.getExpiredDate());
        }

        return pharmacyInfoClient.getPharmacyInfo(pharmacyId, token);
    }
}
