package org.pharmacy.unit.service;

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
import static org.mockito.Mockito.*;
import static org.pharmacy.utils.TestData.USER_INN;
import static org.pharmacy.utils.TestData.getTokenInfoRs;
import static org.pharmacy.utils.TestData.getUserInfoRs;

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

        verify(authClient, times(1)).getUserToken(USER_INN);
        verify(userInfoClient, times(2)).getUserInfo(tokenInfo.getToken());
    }
}
