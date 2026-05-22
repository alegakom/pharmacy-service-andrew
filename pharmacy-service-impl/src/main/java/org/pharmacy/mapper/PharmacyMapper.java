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
    PharmacyRs convertToDto(Pharmacy entity);

    @Mapping(target = "pharmacyChain", ignore = true)
    Pharmacy convertToEntity(CreatePharmacyRq pharmacyRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CreatePharmacyRq source, @MappingTarget Pharmacy target);
}
