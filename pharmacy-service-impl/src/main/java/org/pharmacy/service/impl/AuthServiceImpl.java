package org.pharmacy.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pharmacy.client.AuthClient;
import org.pharmacy.dto.TokenInfoDto;
import org.pharmacy.service.AuthService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;

    @Cacheable(cacheNames = "tokens", key = "#pharmacyId")
    @Override
    public TokenInfoDto getToken(UUID pharmacyId) {
        log.info("Getting new Token");
        return authClient.getToken(pharmacyId);
    }

    @CacheEvict(cacheNames = "tokens", key = "#pharmacyId")
    @Override
    public void revokeToken(UUID pharmacyId) {
        log.info("Revoking Token");
        authClient.revokeToken(pharmacyId);
    }
}
