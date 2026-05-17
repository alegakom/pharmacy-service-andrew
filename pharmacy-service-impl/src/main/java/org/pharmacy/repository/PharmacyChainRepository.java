package org.pharmacy.repository;

import org.pharmacy.entity.PharmacyChain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий для работы с аптечными сетями.
 */
public interface PharmacyChainRepository extends JpaRepository<PharmacyChain, UUID> {

    boolean existsByInn(String inn);
}
