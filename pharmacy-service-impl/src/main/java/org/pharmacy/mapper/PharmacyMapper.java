package org.pharmacy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pharmacy.dto.PharmacyDto;
import org.pharmacy.entity.Pharmacy;
import org.pharmacy.request.CreatePharmacyRequest;

/**
 * Маппер для аптеки.
 */
@Mapper(componentModel = "spring")
public interface PharmacyMapper {

    @Mapping(source = "pharmacyChain.id", target = "pharmacyChainId")
    PharmacyDto toDto(Pharmacy entity);

    @Mapping(target = "pharmacyChain", ignore = true)
    Pharmacy toEntity(CreatePharmacyRequest pharmacyRequest);
}
