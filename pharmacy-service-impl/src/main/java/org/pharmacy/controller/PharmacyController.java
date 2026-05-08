package org.pharmacy.controller;

import lombok.RequiredArgsConstructor;
import org.pharmacy.dto.PharmacyDto;
import org.pharmacy.request.CreatePharmacyRequest;
import org.pharmacy.service.PharmacyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для управления аптеками.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pharmacies")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PharmacyDto create(@RequestBody CreatePharmacyRequest pharmacyRequest) {
        return pharmacyService.create(pharmacyRequest);
    }

    @GetMapping("/{id}")
    public PharmacyDto findById(@PathVariable UUID id) {
        return pharmacyService.findById(id);
    }

    @GetMapping
    public List<PharmacyDto> findAll(@RequestParam(required = false) UUID chainId) {
        if (chainId != null) {
            return pharmacyService.findAllByPharmacyChainId(chainId);
        }
        return pharmacyService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        pharmacyService.deleteById(id);
    }
}
