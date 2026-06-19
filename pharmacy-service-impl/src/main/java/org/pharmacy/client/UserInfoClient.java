package org.pharmacy.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pharmacy.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInfoClient {

    private final RestTemplate restTemplate;

    @Value("${pharmacy-service.user-info.url}")
    private String userInfoUrl;

    public UserInfoDto getUserInfo(String token) {
        log.info("Requesting user info");
        String url = userInfoUrl + token;
        HttpEntity<Void> httpEntity = new HttpEntity<>(getDefaultHeader());
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserInfoDto.class).getBody();
    }

    private HttpHeaders getDefaultHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
