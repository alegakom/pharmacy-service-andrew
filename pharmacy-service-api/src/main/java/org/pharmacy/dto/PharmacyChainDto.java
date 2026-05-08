package org.pharmacy.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * DTO для аптечной сети.
 */
@Value
@Builder
public class PharmacyChainDto {

    UUID id;
    String address;
    boolean locale;
    String shortName;
    String juridicalName;
    String juridicalForm;
}
