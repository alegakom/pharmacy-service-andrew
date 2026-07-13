package org.pharmacy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Информация об аптеке (директор, адрес, ИНН)")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyInfoDto {

    @Schema(description = "Директор аптеки",
            example = "Иванов Иван Иванович")
    private String director;

    @Schema(description = "Адрес аптеки",
            example = "г. Москва, ул. Большая красная, 12")
    private String address;

    @Schema(description = "ИНН аптеки",
            example = "111111111111")
    private String inn;
}
