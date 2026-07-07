package org.pharmacy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Уникальный идентификатор аптечной сети",
            accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Юридический адрес сети аптек")
    private String address;

    @Schema(description = "Флаг локализации сети: false (default) - международная, true - локальная")
    private Boolean locale;

    @Schema(description = "Краткое название сети")
    private String shortName;

    @Schema(description = "ИНН аптечной сети")
    private String inn;

    @Schema(description = "Полное юридическое название организации")
    private String juridicalName;

    @Schema(description = "Юридическая форма (ООО, ПАО, АО, ИП)")
    private String juridicalForm;
}
