package org.pharmacy.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pharmacy.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
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
        return restTemplate.exchange(url, HttpMethod.GET, null, UserInfoDto.class).getBody();
    }
}
