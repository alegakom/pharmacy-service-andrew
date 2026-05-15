package org.pharmacy.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * DTO для аптечной сети.
 */
@Value
@Builder
public class PharmacyChainRs {

    UUID id;
    String address;
    Boolean locale;
    String shortName;
    String inn;
    String juridicalName;
    String juridicalForm;
}
