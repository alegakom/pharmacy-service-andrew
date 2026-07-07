package org.pharmacy.integration.controller;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class IntegrationMocks {

    private static final String AUTH_SERVICE_URL = "/auth-service/api/v1";

    public static void mockGetUserToken(String inn) {
        stubFor(get(AUTH_SERVICE_URL + "/credentials/user/" + inn + "/get-token")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("token-info.json")));
    }

    public static void mockGetUserToken() {
        stubFor(get(urlPathMatching(AUTH_SERVICE_URL + "/credentials/user/[0-9-]*/get-token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("token-info.json")));
    }

    public static void mockGetPharmacyToken(UUID pharmacyId) {
        stubFor(get(AUTH_SERVICE_URL + "/credentials/" + pharmacyId + "/get-token")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("token-info.json")));
    }

    public static void mockGetPharmacyInfo(UUID pharmacyId) {
        stubFor(post(AUTH_SERVICE_URL + "/info/" + pharmacyId)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("pharmacy-info.json")));
    }

    public static void mockGetUserInfo() {
        stubFor(get("/external/api/user/info/")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("user-info.json")));
    }
}
