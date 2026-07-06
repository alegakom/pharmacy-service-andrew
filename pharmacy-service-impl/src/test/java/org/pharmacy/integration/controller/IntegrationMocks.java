package org.pharmacy.integration.controller;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class IntegrationMocks {

    public static void mockGetUserToken(String inn) {
        stubFor(get("/auth-service/api/v1/credentials/user/" + inn + "/get-token")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("token-info.json")));
    }

    public static void mockGetUserToken() {
        stubFor(get(urlPathMatching("/auth-service/api/v1/credentials/user/[0-9-]*/get-token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("token-info.json")));
    }

    public static void mockGetPharmacyToken(UUID pharmacyId) {
        stubFor(get("/auth-service/api/v1/credentials/" + pharmacyId + "/get-token")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("token-info.json")));
    }

    public static void mockGetUserInfo() {
        stubFor(get("/external/api/user/info/")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("user-info.json")));
    }

    public static void mockGetPharmacyInfo(UUID pharmacyId) {
        stubFor(post("/auth-service/api/v1/info/" + pharmacyId)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("pharmacy-info.json")));
    }
}
