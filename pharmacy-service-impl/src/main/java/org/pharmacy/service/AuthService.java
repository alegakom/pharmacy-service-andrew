package org.pharmacy.service;

import org.pharmacy.dto.TokenInfoDto;

import java.util.UUID;

public interface AuthService {

    TokenInfoDto getToken(UUID pharmacyId);

    void revokeToken(UUID pharmacyId);
}
