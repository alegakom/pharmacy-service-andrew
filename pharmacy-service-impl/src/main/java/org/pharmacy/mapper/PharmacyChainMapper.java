package org.pharmacy.mapper;

import org.mapstruct.Mapper;
import org.pharmacy.dto.PharmacyChainDto;
import org.pharmacy.entity.PharmacyChain;
import org.pharmacy.request.CreatePharmacyChainRequest;

/**
 * Маппер для аптечной сети.
 */
@Mapper(componentModel = "spring")
public interface PharmacyChainMapper {

    PharmacyChainDto toDto(PharmacyChain entity);

    PharmacyChain toEntity(CreatePharmacyChainRequest pharmacyChainRequest);
}
