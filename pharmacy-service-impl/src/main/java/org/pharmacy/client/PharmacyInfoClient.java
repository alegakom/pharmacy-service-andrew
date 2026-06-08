package org.pharmacy.client;

import lombok.RequiredArgsConstructor;
import org.pharmacy.dto.PharmacyInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PharmacyInfoClient {

    private final RestTemplate restTemplate;

    @Value("${auth-service.pharmacy-info.url}")
    private String pharmacyInfoUrl;

    public PharmacyInfoDto getPharmacyInfo(UUID pharmacyId, String token) {
        String url = pharmacyInfoUrl + pharmacyId;
        HttpEntity<String> httpEntity = new HttpEntity<>(token);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, PharmacyInfoDto.class).getBody();
    }
}
