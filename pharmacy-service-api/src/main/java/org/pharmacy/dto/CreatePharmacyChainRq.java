package org.pharmacy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request-DTO для аптечной сети.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePharmacyChainRq {

    public interface onCreate {}
    public interface onUpdate {}

    @NotBlank(groups = CreatePharmacyChainRq.onCreate.class)
    @Size(max = 255)
    private String address;

    @NotNull(groups = CreatePharmacyChainRq.onCreate.class)
    private Boolean locale;

    @Size(max = 32)
    private String shortName;

    @NotNull(groups = CreatePharmacyChainRq.onCreate.class)
    @Pattern(regexp = "\\d{10}|\\d{12}")
    private String inn;

    @NotBlank(groups = CreatePharmacyChainRq.onCreate.class)
    @Size(max = 64)
    private String juridicalName;

    @NotBlank(groups = CreatePharmacyChainRq.onCreate.class)
    @Size(max = 8)
    private String juridicalForm;
}
