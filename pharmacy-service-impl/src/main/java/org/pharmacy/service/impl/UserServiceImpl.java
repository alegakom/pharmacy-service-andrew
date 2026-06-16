package org.pharmacy.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pharmacy.client.AuthClient;
import org.pharmacy.client.UserInfoClient;
import org.pharmacy.dto.TokenInfoDto;
import org.pharmacy.dto.UserInfoDto;
import org.pharmacy.service.UserService;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final AuthClient authClient;
    private final UserInfoClient userInfoClient;

    @Override
    public UserInfoDto getUserInfo(String inn) {
        log.info("Getting user info by inn: {}", inn);

        TokenInfoDto tokenInfo = authClient.getUserToken(inn);
        log.debug("Received user token for inn: {}", inn);

        UserInfoDto userInfo = userInfoClient.getUserInfo(tokenInfo.getToken());
        log.debug("Received user info for inn: {}", inn);

        return userInfo;
    }
}
