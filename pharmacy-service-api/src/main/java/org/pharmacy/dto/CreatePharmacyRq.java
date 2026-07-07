package org.pharmacy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Адрес аптеки",
            example = "г. Москва, ул. Тверская, д. 1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(groups = onCreate.class)
    @Size(max = 255)
    private String address;

    @Schema(description = "Название аптеки",
            example = "Аптека №1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(groups = onCreate.class)
    @Size(max = 64)
    private String name;

    @Schema(description = "ИНН аптеки (10 или 12 цифр)",
            example = "123456789012",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(groups = onCreate.class)
    @Pattern(regexp = "\\d{10}|\\d{12}")
    private String inn;

    @Schema(description = "Категория аптеки (максимум 5 символов)",
            example = "A1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(groups = onCreate.class)
    @Size(max = 5)
    private String category;

    @Schema(description = "Юридическая форма (ООО, ПАО, АО, ИП)",
            example = "ООО",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(groups = onCreate.class)
    @Size(max = 8)
    private String juridicalForm;

    @Schema(description = "ID аптечной сети, к которой привязана аптека")
    private UUID pharmacyChainId;
}
