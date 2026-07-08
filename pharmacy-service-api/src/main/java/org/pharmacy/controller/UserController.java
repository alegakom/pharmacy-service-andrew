package org.pharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pharmacy.dto.UserInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
@Tag(name = "User API", description = "Управление пользователями и получение информации о пользователях")
public interface UserController {

    @Operation(summary = "Получить информацию о пользователе по ID",
            description = "Возвращает информацию о пользователе (имя, дата рождения и т.д.)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о пользователе получена",
                    content = @Content(schema = @Schema(implementation = UserInfoDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/info/{inn}")
    UserInfoDto getUserInfo(@Parameter(description = "ИНН пользователя (10 или 12 цифр)", required = true)
                            @PathVariable String inn);
}
