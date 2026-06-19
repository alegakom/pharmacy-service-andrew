package org.pharmacy.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pharmacy.client.AuthClient;
import org.pharmacy.client.UserInfoClient;
import org.pharmacy.dto.TokenInfoDto;
import org.pharmacy.dto.UserInfoDto;
import org.pharmacy.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.pharmacy.TestData.USER_INN;
import static org.pharmacy.TestData.getTokenInfoRs;
import static org.pharmacy.TestData.getUserInfoRs;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private AuthClient authClient;

    @Mock
    private UserInfoClient userInfoClient;

    @Test
    void getUserInfoTest() {
        TokenInfoDto tokenInfo = getTokenInfoRs();
        UserInfoDto expectedUserInfo = getUserInfoRs();

        when(authClient.getUserToken(USER_INN)).thenReturn(tokenInfo);
        when(userInfoClient.getUserInfo(tokenInfo.getToken())).thenReturn(expectedUserInfo);

        UserInfoDto actualUserInfo = userService.getUserInfo(USER_INN);

        assertNotNull(actualUserInfo);
        assertEquals(expectedUserInfo, actualUserInfo);

        verify(authClient).getUserToken(USER_INN);
        verify(userInfoClient).getUserInfo(tokenInfo.getToken());
    }
}
