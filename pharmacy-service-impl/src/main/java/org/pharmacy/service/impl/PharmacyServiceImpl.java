package org.pharmacy.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.pharmacy.service.PharmacyService;
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
@Slf4j
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final PharmacyChainRepository pharmacyChainRepository;
    private final PharmacyMapper pharmacyMapper;
    private final AuthClient authClient;
    private final PharmacyInfoClient pharmacyInfoClient;

    @Override
    @Transactional
    public PharmacyRs create(CreatePharmacyRq pharmacyRq) {
        log.info("Creating pharmacy with inn: {}", pharmacyRq.getInn());
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
        log.debug("Pharmacy created: {}", saved.getId());
        return pharmacyMapper.convertToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PharmacyRs findById(UUID id) {
        log.info("Finding pharmacy by id: {}", id);
        return pharmacyRepository.findById(id)
                .map(pharmacyMapper::convertToDto)
                .orElseThrow(() -> new NotFoundCrmException(
                        String.format(ExceptionMessageConstants.PHARMACY_NOT_FOUND, id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PharmacyRs> findAllByPharmacyChainId(UUID pharmacyChainId) {
        log.info("Finding all pharmacies by chain id: {}", pharmacyChainId);
        return pharmacyRepository.findAllByPharmacyChainId(pharmacyChainId).stream()
                .map(pharmacyMapper::convertToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PharmacyRs> findAll() {
        log.info("Finding all pharmacies");
        return pharmacyRepository.findAll().stream()
                .map(pharmacyMapper::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public PharmacyRs update(UUID id, CreatePharmacyRq pharmacyRq) {
        log.info("Updating pharmacy with id: {}", id);
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
        log.debug("Pharmacy updated: {}", pharmacy.getId());
        return pharmacyMapper.convertToDto(pharmacy);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        log.info("Deleting pharmacy with id: {}", id);
        if (!pharmacyRepository.existsById(id)) {
            throw new NotFoundCrmException(
                    String.format(ExceptionMessageConstants.PHARMACY_NOT_FOUND, id));
        }
        pharmacyRepository.deleteById(id);
        log.debug("Pharmacy deleted: {}", id);
    }

    @Override
    @Transactional
    public PharmacyInfoDto getPharmacyInfo(UUID pharmacyId) {
        log.info("Getting pharmacy info for id: {}", pharmacyId);
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new NotFoundCrmException(
                        String.format(ExceptionMessageConstants.PHARMACY_NOT_FOUND, pharmacyId)));

        String token = pharmacy.getToken();
        LocalDate expiredDate = pharmacy.getExpiredDate();
        if (token == null || expiredDate == null || expiredDate.isBefore(LocalDate.now())) {
            log.warn("Token is missing or expired for pharmacy id: {}, requesting new token", pharmacyId);
            TokenInfoDto tokenInfo = authClient.getToken(pharmacyId);
            token = tokenInfo.getToken();
            pharmacy.setToken(token);
            pharmacy.setExpiredDate(tokenInfo.getExpiredDate());
            log.debug("New token received for pharmacy id: {}, expires: {}", pharmacyId, tokenInfo.getExpiredDate());
        }

        return pharmacyInfoClient.getPharmacyInfo(pharmacyId, token);
    }
}
