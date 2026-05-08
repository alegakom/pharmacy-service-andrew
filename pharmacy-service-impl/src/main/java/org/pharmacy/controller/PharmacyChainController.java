package org.pharmacy.controller;

import lombok.RequiredArgsConstructor;
import org.pharmacy.dto.PharmacyChainDto;
import org.pharmacy.request.CreatePharmacyChainRequest;
import org.pharmacy.service.PharmacyChainService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для управления аптечными сетями.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pharmacy-chain")
public class PharmacyChainController {

    private final PharmacyChainService pharmacyChainService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PharmacyChainDto create(@RequestBody CreatePharmacyChainRequest pharmacyChainRequest) {
        return pharmacyChainService.create(pharmacyChainRequest);
    }

    @GetMapping("/{id}")
    public PharmacyChainDto findById(@PathVariable UUID id) {
        return pharmacyChainService.findById(id);
    }

    @GetMapping
    public List<PharmacyChainDto> findAll() {
        return pharmacyChainService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        pharmacyChainService.deleteById(id);
    }
}
