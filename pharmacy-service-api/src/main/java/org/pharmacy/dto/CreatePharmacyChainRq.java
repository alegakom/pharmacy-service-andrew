package org.pharmacy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

/**
 * Request-DTO для аптечной сети.
 */
@Value
@Builder
public class CreatePharmacyChainRq {

    public interface onCreate {}
    public interface onUpdate {}

    @NotBlank(groups = CreatePharmacyChainRq.onCreate.class)
    @Size(max = 255)
    String address;

    @NotNull(groups = CreatePharmacyChainRq.onCreate.class)
    Boolean locale;

    @Size(max = 32)
    String shortName;

    @NotNull(groups = CreatePharmacyChainRq.onCreate.class)
    @Pattern(regexp = "\\d{10}|\\d{12}")
    String inn;

    @NotBlank(groups = CreatePharmacyChainRq.onCreate.class)
    @Size(max = 64)
    String juridicalName;

    @NotBlank(groups = CreatePharmacyChainRq.onCreate.class)
    @Size(max = 8)
    String juridicalForm;
}
