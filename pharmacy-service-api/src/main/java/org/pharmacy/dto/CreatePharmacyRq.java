package org.pharmacy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request-DTO для аптеки.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePharmacyRq {

    public interface onCreate {}
    public interface onUpdate {}

    @NotBlank(groups = onCreate.class)
    @Size(max = 255)
    private String address;

    @NotBlank(groups = onCreate.class)
    @Size(max = 64)
    private String name;

    @NotNull(groups = onCreate.class)
    @Pattern(regexp = "\\d{10}|\\d{12}")
    private String inn;

    @NotBlank(groups = onCreate.class)
    @Size(max = 5)
    private String category;

    @NotBlank(groups = onCreate.class)
    @Size(max = 8)
    private String juridicalForm;

    private UUID pharmacyChainId;
}
