package org.pharmacy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Schema(description = "Ответ с данными аптеки")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyRs {

    @Schema(description = "Уникальный идентификатор аптеки",
            accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Адрес аптеки")
    private String address;

    @Schema(description = "Название аптеки")
    private String name;

    @Schema(description = "ИНН аптеки")
    private String inn;

    @Schema(description = "Категория аптеки")
    private String category;

    @Schema(description = "Юридическая форма")
    private String juridicalForm;

    @Schema(description = "ID аптечной сети, к которой привязана аптека")
    private UUID pharmacyChainId;
}
