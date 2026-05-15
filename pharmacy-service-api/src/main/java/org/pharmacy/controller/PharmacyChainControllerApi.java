package org.pharmacy.controller;

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
public interface PharmacyChainControllerApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PharmacyChainRs create(@Validated(CreatePharmacyChainRq.onCreate.class) @RequestBody CreatePharmacyChainRq pharmacyChainRequest);

    @GetMapping("/{id}")
    PharmacyChainRs findById(@PathVariable UUID id);

    @GetMapping
    List<PharmacyChainRs> findAll();

    @PatchMapping("/{id}")
    PharmacyChainRs update(@PathVariable UUID id,
                      @Validated(CreatePharmacyChainRq.onUpdate.class) @RequestBody CreatePharmacyChainRq pharmacyChainRq);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable UUID id);
}
