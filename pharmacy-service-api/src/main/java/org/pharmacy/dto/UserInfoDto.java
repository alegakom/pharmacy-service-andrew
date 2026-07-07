package org.pharmacy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    @Schema(description = "Уникальный идентификатор пользователя",
            accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Имя пользователя",
            example = "Иван")
    private String userName;

    @Schema(description = "Дата рождения пользователя",
            example = "2003-10-05")
    private LocalDate birthDate;
}
