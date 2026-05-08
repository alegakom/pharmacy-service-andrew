package org.pharmacy.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * DTO для аптеки.
 */
@Value
@Builder
public class PharmacyDto {

    UUID id;
    String address;
    String name;
    Long inn;
    String category;
    String juridicalForm;
    UUID pharmacyChainId;
}
