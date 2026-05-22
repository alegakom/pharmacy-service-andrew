package org.pharmacy.controller;

import org.pharmacy.dto.PharmacyRs;
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
@RequestMapping("/api/v1/pharmacies")
public interface PharmacyController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PharmacyRs create(@Validated(CreatePharmacyRq.onCreate.class) @RequestBody CreatePharmacyRq pharmacyRequest);

    @GetMapping("/{id}")
    PharmacyRs findById(@PathVariable UUID id);

    @GetMapping
    List<PharmacyRs> findAll(@RequestParam(required = false) UUID chainId);

    @PatchMapping("/{id}")
    PharmacyRs update(@PathVariable UUID id,
                      @Validated(CreatePharmacyRq.onUpdate.class) @RequestBody CreatePharmacyRq pharmacyRq);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable UUID id);
}
