package org.pharmacy.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * Response-DTO для аптеки.
 */
@Value
@Builder
public class PharmacyRs {

    UUID id;
    String address;
    String name;
    String inn;
    String category;
    String juridicalForm;
    UUID pharmacyChainId;
}
