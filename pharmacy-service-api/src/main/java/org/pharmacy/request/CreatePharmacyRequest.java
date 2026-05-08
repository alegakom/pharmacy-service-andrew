package org.pharmacy.request;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * Request-DTO для аптеки.
 */
@Value
@Builder
public class CreatePharmacyRequest {

    String address;
    String name;
    Long inn;
    String category;
    String juridicalForm;
    UUID pharmacyChainId;
}
