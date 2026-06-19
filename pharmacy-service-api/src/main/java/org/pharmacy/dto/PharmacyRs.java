package org.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Response-DTO для аптеки.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyRs {
    private UUID id;
    private String address;
    private String name;
    private String inn;
    private String category;
    private String juridicalForm;
    private UUID pharmacyChainId;
}
