package org.pharmacy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Positive
    Long inn;

    @NotBlank(groups = onCreate.class)
    @Size(max = 5)
    String category;

    @NotBlank(groups = onCreate.class)
    @Size(max = 8)
    String juridicalForm;

    UUID pharmacyChainId;
}
