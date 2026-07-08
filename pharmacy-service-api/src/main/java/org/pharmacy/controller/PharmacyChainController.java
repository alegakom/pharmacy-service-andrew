package org.pharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pharmacy.dto.CreatePharmacyChainRq;
import org.pharmacy.dto.PharmacyChainRs;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс REST-контроллера для управления аптечными сетями.
 */
@RequestMapping("/api/v1/pharmacy-chain")
@Tag(name = "Pharmacy Chain API", description = "Управление аптечными сетями")
public interface PharmacyChainController {

    @Operation(summary = "Создать новую аптечную сеть",
            description = "Создаёт аптечную сеть с указанными данными. ИНН должен быть уникальным.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Аптечная сеть создана",
                    content = @Content(schema = @Schema(implementation = PharmacyChainRs.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные данные или дубликат INN",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PharmacyChainRs create(@Validated(CreatePharmacyChainRq.onCreate.class) @RequestBody CreatePharmacyChainRq pharmacyChainRequest);

    @Operation(summary = "Получить аптечную сеть по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Сеть найдена",
                    content = @Content(schema = @Schema(implementation = PharmacyChainRs.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Сеть не найдена",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    PharmacyChainRs findById(@Parameter(description = "ID аптечной сети", required = true) @PathVariable UUID id);

    @Operation(summary = "Получить список всех аптечных сетей")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список получен",
                    content = @Content(schema = @Schema(implementation = PharmacyChainRs[].class)))
    })
    @GetMapping
    List<PharmacyChainRs> findAll();

    @Operation(summary = "Обновить аптечную сеть")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Сеть обновлена",
                    content = @Content(schema = @Schema(implementation = PharmacyChainRs.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Сеть не найдена",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PatchMapping("/{id}")
    PharmacyChainRs update(@Parameter(description = "ID аптечной сети") @PathVariable UUID id,
                      @Validated(CreatePharmacyChainRq.onUpdate.class) @RequestBody CreatePharmacyChainRq pharmacyChainRq);

    @Operation(summary = "Удалить аптечную сеть")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Сеть удалена"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Сеть не найдена",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "Попытка удалить служебную запись 'Без сети'",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@Parameter(description = "ID аптечной сети") @PathVariable UUID id);
}
