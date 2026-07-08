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

    @Schema(description = "Юридический адрес сети аптек",
            example = "г. Москва, ул. Тверская, д. 10",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(groups = CreatePharmacyChainRq.onCreate.class)
    @Size(max = 255)
    private String address;

    @Schema(description = "Флаг локализации сети: false (default) - международная, true - локальная",
            example = "false")
    @NotNull(groups = CreatePharmacyChainRq.onCreate.class)
    private Boolean locale;

    @Schema(description = "Краткое название сети",
            example = "Фарм-Сеть")
    @Size(max = 32)
    private String shortName;

    @Schema(description = "ИНН аптечной сети (10 или 12 цифр)",
            example = "123456789012",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(groups = CreatePharmacyChainRq.onCreate.class)
    @Pattern(regexp = "\\d{10}|\\d{12}")
    private String inn;

    @Schema(description = "Полное юридическое название организации",
            example = "Общество с ограниченной ответственностью 'Фарм-Сеть'",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(groups = CreatePharmacyChainRq.onCreate.class)
    @Size(max = 64)
    private String juridicalName;

    @Schema(description = "Юридическая форма (ООО, ПАО, АО, ИП)",
            example = "ООО",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(groups = CreatePharmacyChainRq.onCreate.class)
    @Size(max = 8)
    private String juridicalForm;
}
