package org.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO для аптечной сети.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyChainRs {
    private UUID id;
    private String address;
    private Boolean locale;
    private String shortName;
    private String inn;
    private String juridicalName;
    private String juridicalForm;
}
