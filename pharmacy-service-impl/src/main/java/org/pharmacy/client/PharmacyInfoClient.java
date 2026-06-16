package org.pharmacy.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pharmacy.dto.PharmacyInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PharmacyInfoClient {

    private final RestTemplate restTemplate;

    @Value("${pharmacy-service.pharmacy-info.url}")
    private String pharmacyInfoUrl;

    public PharmacyInfoDto getPharmacyInfo(UUID pharmacyId, String token) {
        log.info("Requesting pharmacy info for id: {}", pharmacyId);
        String url = pharmacyInfoUrl + pharmacyId;
        HttpEntity<String> httpEntity = new HttpEntity<>(token);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, PharmacyInfoDto.class).getBody();
    }
}
