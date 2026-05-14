package org.pharmacy.repository;

import org.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с аптеками.
 */
public interface PharmacyRepository extends JpaRepository<Pharmacy, UUID> {

    List<Pharmacy> findAllByPharmacyChainId(UUID pharmacyChainId);

    List<Pharmacy> findAllByPharmacyChainIsNull();
}
