package org.pharmacy.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * Request-DTO для аптеки.
 */
@Value
@Builder
public class CreatePharmacyRq {

    public interface onCreate {}
    public interface onUpdate {}

    @NotBlank(groups = onCreate.class)
    @Size(max = 255)
    String address;

    @NotBlank(groups = onCreate.class)
    @Size(max = 64)
    String name;

    @NotNull(groups = onCreate.class)
    @Pattern(regexp = "\\d{10}|\\d{12}")
    String inn;

    @NotBlank(groups = onCreate.class)
    @Size(max = 5)
    String category;

    @NotBlank(groups = onCreate.class)
    @Size(max = 8)
    String juridicalForm;

    UUID pharmacyChainId;
}
