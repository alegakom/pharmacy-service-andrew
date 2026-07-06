package org.pharmacy.integration.controller;

import io.restassured.RestAssured;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.pharmacy.repository.PharmacyChainRepository;
import org.pharmacy.repository.PharmacyRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9092)
@AutoConfigureEmbeddedDatabase(provider = DatabaseProvider.ZONKY,
        refresh = RefreshMode.BEFORE_EACH_TEST_METHOD,
        type = DatabaseType.POSTGRES)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles({"test", "dev"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractControllerIT {

    @MockitoSpyBean
    protected PharmacyRepository pharmacyRepository;

    @MockitoSpyBean
    protected PharmacyChainRepository pharmacyChainRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/pharmacy-service";
    }
}
