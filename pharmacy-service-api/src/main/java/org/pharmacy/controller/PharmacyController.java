package org.pharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pharmacy.dto.PharmacyRs;
import org.pharmacy.dto.PharmacyInfoDto;
import org.pharmacy.dto.CreatePharmacyRq;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс REST-контроллера для управления аптекой.
 */
@RequestMapping("/api/v1/pharmacy")
@Tag(name = "Pharmacy API", description = "Управление аптеками")
public interface PharmacyController {

    @Operation(summary = "Создать новую аптеку",
            description = "Создаёт аптеку с указанными данными. Если pharmacyChainId не указан, аптека создаётся без сети.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Аптека успешно создана",
                    content = @Content(schema = @Schema(implementation = PharmacyRs.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные входные данные или дубликат INN",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PharmacyRs create(@Validated(CreatePharmacyRq.onCreate.class) @RequestBody CreatePharmacyRq pharmacyRequest);

    @Operation(summary = "Получить аптеку по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Аптека найдена",
                    content = @Content(schema = @Schema(implementation = PharmacyRs.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Аптека не найдена",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    PharmacyRs findById(@Parameter(description = "ID аптеки", required = true) @PathVariable UUID id);

    @Operation(summary = "Получить список аптек",
            description = "Можно отфильтровать по ID аптечной сети")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список получен",
                    content = @Content(schema = @Schema(implementation = PharmacyRs[].class)))
    })
    @GetMapping
    List<PharmacyRs> findAll(@Parameter(description = "ID аптечной сети для фильтрации")
                             @RequestParam(required = false) UUID chainId);

    @Operation(summary = "Обновить аптеку")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Аптека обновлена",
                    content = @Content(schema = @Schema(implementation = PharmacyRs.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Аптека не найдена",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PatchMapping("/{id}")
    PharmacyRs update(@Parameter(description = "ID аптеки") @PathVariable UUID id,
                      @Validated(CreatePharmacyRq.onUpdate.class) @RequestBody CreatePharmacyRq pharmacyRq);

    @Operation(summary = "Удалить аптеку")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Аптека удалена"), // без content, т.к. тело пустое
            @ApiResponse(
                    responseCode = "400",
                    description = "Аптека не найдена",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@Parameter(description = "ID аптеки") @PathVariable UUID id);

    @Operation(summary = "Получить расширенную информацию об аптеке",
            description = "Возвращает ИНН, адрес и директора аптеки. При необходимости автоматически обновляет токен.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация получена",
                    content = @Content(schema = @Schema(implementation = PharmacyInfoDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Аптека не найдена",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/info/{pharmacyId}")
    PharmacyInfoDto getPharmacyInfo(@Parameter(description = "ID аптеки") @PathVariable UUID pharmacyId);

    @Operation(summary = "Отозвать токен аптеки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Токен отозван"),
            @ApiResponse(responseCode = "404", description = "Токен не найден")
    })
    @DeleteMapping("/{pharmacyId}/revoke-token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void revokeToken(@Parameter(description = "ID аптеки") @PathVariable UUID pharmacyId);

    @Operation(summary = "Проверить актуальность и обновить токен аптеки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен актуален"),
    })
    @PostMapping("/checking-token")
    @ResponseStatus(HttpStatus.OK)
    void checkingToken(@Schema(description = "Список ID аптек для проверки. Если null или пустой, проверяются все аптеки.") @RequestBody List<UUID> pharmacyIds);
}
