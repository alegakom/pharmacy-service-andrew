package org.pharmacy.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.pharmacy.dto.CreatePharmacyChainRq;
import org.pharmacy.dto.PharmacyChainRs;
import org.pharmacy.entity.PharmacyChain;

/**
 * Маппер для аптечной сети.
 */
@Mapper(componentModel = "spring")
public interface PharmacyChainMapper {

    PharmacyChainRs toDto(PharmacyChain entity);

    PharmacyChain toEntity(CreatePharmacyChainRq pharmacyChainRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CreatePharmacyChainRq source, @MappingTarget PharmacyChain target);
}
