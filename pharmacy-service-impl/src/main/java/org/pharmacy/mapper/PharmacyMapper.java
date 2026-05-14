package org.pharmacy.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.pharmacy.dto.PharmacyRs;
import org.pharmacy.entity.Pharmacy;
import org.pharmacy.dto.CreatePharmacyRq;

/**
 * Маппер для аптеки.
 */
@Mapper(componentModel = "spring")
public interface PharmacyMapper {

    @Mapping(source = "pharmacyChain.id", target = "pharmacyChainId")
    PharmacyRs toDto(Pharmacy entity);

    @Mapping(target = "pharmacyChain", ignore = true)
    Pharmacy toEntity(CreatePharmacyRq pharmacyRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CreatePharmacyRq source, @MappingTarget Pharmacy target);
}
