package org.pharmacy.integration.controller;

import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.pharmacy.dto.UserInfoDto;
import org.pharmacy.utils.TestData;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.pharmacy.integration.controller.IntegrationMocks.mockGetUserToken;
import static org.pharmacy.integration.controller.IntegrationMocks.mockGetUserInfo;
import static org.pharmacy.utils.TestData.USER_INN;
import static org.pharmacy.utils.TestData.getUserInfoRs;

@RequiredArgsConstructor
@DisplayName("Тест для работы с пользовательским контроллером")
class UserControllerIT extends AbstractControllerIT {

    @ParameterizedTest
    @ValueSource(strings = {"123456789098", "098765432134", USER_INN})
    @DisplayName("Тест метода для получения данных пользователя")
    void getUserInfoTest(String inn) {
        mockGetUserToken(inn);
        mockGetUserInfo();
        var response = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/user/info/{inn}", inn)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(UserInfoDto.class);

        UserInfoDto userInfoRs = getUserInfoRs();
        assertEquals(userInfoRs.getId(), response.getId());
        assertEquals(userInfoRs.getUserName(), response.getUserName());
        assertEquals(userInfoRs.getBirthDate(), response.getBirthDate());
    }

    @Test
    @DisplayName("Тест метода для получения данных пользователя с любым ИНН")
    void getUserInfoUniversalInnStubTest() {
        mockGetUserToken();
        mockGetUserInfo();
        var response = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/user/info/{inn}", TestData.generateRandomInn())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(UserInfoDto.class);

        UserInfoDto userInfoRs = getUserInfoRs();
        assertEquals(userInfoRs.getId(), response.getId());
        assertEquals(userInfoRs.getUserName(), response.getUserName());
        assertEquals(userInfoRs.getBirthDate(), response.getBirthDate());
    }
}
