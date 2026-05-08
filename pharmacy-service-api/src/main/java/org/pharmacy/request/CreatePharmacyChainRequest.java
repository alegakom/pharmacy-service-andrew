package org.pharmacy.request;

import lombok.Builder;
import lombok.Value;

/**
 * Request-DTO для аптечной сети.
 */
@Value
@Builder
public class CreatePharmacyChainRequest {

    String address;
    boolean locale;
    String shortName;
    String juridicalName;
    String juridicalForm;
}
