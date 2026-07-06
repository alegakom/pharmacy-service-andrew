package org.pharmacy.integration.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.pharmacy.dto.CreatePharmacyRq;
import org.pharmacy.dto.PharmacyInfoDto;
import org.pharmacy.dto.PharmacyRs;
import org.pharmacy.entity.Pharmacy;
import org.pharmacy.entity.PharmacyChain;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.pharmacy.integration.controller.IntegrationMocks.mockGetPharmacyInfo;
import static org.pharmacy.integration.controller.IntegrationMocks.mockGetPharmacyToken;
import static org.pharmacy.utils.TestData.*;

@DisplayName("Тест сервиса для работы с аптекой")
class PharmacyControllerIT extends AbstractControllerIT {

    // Вспомогательный метод для создания аптечной сети через репозиторий
    private PharmacyChain createPharmacyChain() {
        return pharmacyChainRepository.save(PharmacyChain.builder()
                .address("Адрес сети " + UUID.randomUUID())
                .locale(false)
                .inn(generateRandomInn())
                .juridicalName("Юр. лицо " + UUID.randomUUID())
                .juridicalForm("ООО")
                .build());
    }

    // Вспомогательный метод для создания аптеки через API
    private PharmacyRs createPharmacyViaApi(CreatePharmacyRq createPharmacyRq) {
        return given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .body(createPharmacyRq)
                .when()
                .post("/api/v1/pharmacy")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(PharmacyRs.class);
    }

    @Test
    @DisplayName("Cоздание аптеки")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Order(1)
    void createTest() {
        PharmacyChain chain = createPharmacyChain();
        CreatePharmacyRq request = createRandomPharmacyRq(chain.getId());

        PharmacyRs response = createPharmacyViaApi(request);

        // Проверяем ответ
        assertNotNull(response.getId());
        assertEquals(request.getAddress(), response.getAddress());
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getInn(), response.getInn());
        assertEquals(request.getCategory(), response.getCategory());
        assertEquals(request.getJuridicalForm(), response.getJuridicalForm());
        assertEquals(chain.getId(), response.getPharmacyChainId());

        // Проверяем в БД
        Optional<Pharmacy> pharmacyOpt = pharmacyRepository.findById(response.getId());
        assertTrue(pharmacyOpt.isPresent());
        Pharmacy pharmacy = pharmacyOpt.get();
        assertEquals(request.getAddress(), pharmacy.getAddress());
        assertEquals(request.getName(), pharmacy.getName());
        assertEquals(request.getInn(), pharmacy.getInn());
        assertEquals(request.getCategory(), pharmacy.getCategory());
        assertEquals(request.getJuridicalForm(), pharmacy.getJuridicalForm());
        assertEquals(chain.getId(), pharmacy.getPharmacyChain().getId());
    }

    @Test
    @DisplayName("Поиск аптеки по id")
    @Order(2)
    void findByIdTest() {
        PharmacyChain chain = createPharmacyChain();
        CreatePharmacyRq request = createRandomPharmacyRq(chain.getId());
        PharmacyRs expectedPharmacy = createPharmacyViaApi(request);

        PharmacyRs actualPharmacy = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/pharmacy/{id}", expectedPharmacy.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PharmacyRs.class);

        assertEquals(expectedPharmacy.getId(), actualPharmacy.getId());
        assertEquals(expectedPharmacy.getAddress(), actualPharmacy.getAddress());
        assertEquals(expectedPharmacy.getName(), actualPharmacy.getName());
        assertEquals(expectedPharmacy.getInn(), actualPharmacy.getInn());
        assertEquals(expectedPharmacy.getCategory(), actualPharmacy.getCategory());
        assertEquals(expectedPharmacy.getJuridicalForm(), actualPharmacy.getJuridicalForm());
        assertEquals(expectedPharmacy.getPharmacyChainId(), actualPharmacy.getPharmacyChainId());
    }

    @Test
    @DisplayName("Поиск всех аптек без фильтра и с фильтром по сети")
    @Order(3)
    void findAllTest() {
        // Создаём две сети
        PharmacyChain chain1 = createPharmacyChain();
        PharmacyChain chain2 = createPharmacyChain();

        // Создаём 3 аптеки: 2 в chain1, 1 в chain2
        CreatePharmacyRq rq1 = createRandomPharmacyRq(chain1.getId());
        CreatePharmacyRq rq2 = createRandomPharmacyRq(chain1.getId());
        CreatePharmacyRq rq3 = createRandomPharmacyRq(chain2.getId());

        createPharmacyViaApi(rq1);
        createPharmacyViaApi(rq2);
        createPharmacyViaApi(rq3);

        // 1. Без фильтра – все 3
        List<PharmacyRs> all = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/pharmacy")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath().getList(".", PharmacyRs.class);

        assertEquals(3, all.size());

        // 2. С фильтром по chain1 – 2 аптеки
        List<PharmacyRs> filtered = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .param("chainId", chain1.getId())
                .when()
                .get("/api/v1/pharmacy")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath().getList(".", PharmacyRs.class);

        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().allMatch(p -> p.getPharmacyChainId().equals(chain1.getId())));

        // 3. С фильтром по chain2 – 1 аптека
        filtered = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .param("chainId", chain2.getId())
                .when()
                .get("/api/v1/pharmacy")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath().getList(".", PharmacyRs.class);

        assertEquals(1, filtered.size());
        assertEquals(chain2.getId(), filtered.get(0).getPharmacyChainId());
    }

    @Test
    @DisplayName("Обновление аптеки")
    @Order(4)
    void updateTest() {
        PharmacyChain chain = createPharmacyChain();
        CreatePharmacyRq createRq = createRandomPharmacyRq(chain.getId());
        PharmacyRs created = createPharmacyViaApi(createRq);

        // Новые данные для обновления
        CreatePharmacyRq expectedPharmacy = CreatePharmacyRq.builder()
                .address("Обновлённый адрес " + UUID.randomUUID())
                .name("Обновлённое имя " + UUID.randomUUID())
                .inn(generateRandomInn())
                .category("B2")
                .juridicalForm("ЗАО")
                .pharmacyChainId(chain.getId())
                .build();

        PharmacyRs updatedPharmacy = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .body(expectedPharmacy)
                .when()
                .patch("/api/v1/pharmacy/{id}", created.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PharmacyRs.class);

        // Проверяем ответ
        assertEquals(created.getId(), updatedPharmacy.getId());
        assertEquals(expectedPharmacy.getAddress(), updatedPharmacy.getAddress());
        assertEquals(expectedPharmacy.getName(), updatedPharmacy.getName());
        assertEquals(expectedPharmacy.getInn(), updatedPharmacy.getInn());
        assertEquals(expectedPharmacy.getCategory(), updatedPharmacy.getCategory());
        assertEquals(expectedPharmacy.getJuridicalForm(), updatedPharmacy.getJuridicalForm());
        assertEquals(expectedPharmacy.getPharmacyChainId(), updatedPharmacy.getPharmacyChainId());

        // Проверяем в БД
        Optional<Pharmacy> pharmacyOpt = pharmacyRepository.findById(created.getId());
        assertTrue(pharmacyOpt.isPresent());
        Pharmacy pharmacy = pharmacyOpt.get();
        assertEquals(expectedPharmacy.getAddress(), pharmacy.getAddress());
        assertEquals(expectedPharmacy.getName(), pharmacy.getName());
        assertEquals(expectedPharmacy.getInn(), pharmacy.getInn());
        assertEquals(expectedPharmacy.getCategory(), pharmacy.getCategory());
        assertEquals(expectedPharmacy.getJuridicalForm(), pharmacy.getJuridicalForm());
        assertEquals(expectedPharmacy.getPharmacyChainId(), pharmacy.getPharmacyChain().getId());
    }

    @Test
    @DisplayName("Удаление аптеки по id")
    @Order(5)
    void deleteByIdTest() {
        PharmacyChain chain = createPharmacyChain();
        CreatePharmacyRq createRq = createRandomPharmacyRq(chain.getId());
        PharmacyRs created = createPharmacyViaApi(createRq);

        // Удаляем
        given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/pharmacy/{id}", created.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Проверяем, что в БД нет
        Optional<Pharmacy> pharmacyOpt = pharmacyRepository.findById(created.getId());
        assertFalse(pharmacyOpt.isPresent());
    }

    @Test
    @DisplayName("Получение детальной информации об аптеке")
    @Order(6)
    void getPharmacyInfoTest() {
        PharmacyChain chain = createPharmacyChain();
        CreatePharmacyRq createRq = createRandomPharmacyRq(chain.getId());
        PharmacyRs createdPharmacy = createPharmacyViaApi(createRq);

        mockGetPharmacyToken(createdPharmacy.getId());
        mockGetPharmacyInfo(createdPharmacy.getId());

        PharmacyInfoDto actualPharmacyInfo = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/pharmacy/info/{pharmacyId}", createdPharmacy.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PharmacyInfoDto.class);

        PharmacyInfoDto expectedPharmacyInfo = getPharmacyInfoRs();

        assertNotNull(expectedPharmacyInfo.getDirector(), actualPharmacyInfo.getDirector());
        assertEquals(expectedPharmacyInfo.getAddress(), actualPharmacyInfo.getAddress());
        assertEquals(expectedPharmacyInfo.getInn(), actualPharmacyInfo.getInn());
    }

    @Test
    @DisplayName("Поиск по несуществующему id возвращает 400")
    @Order(7)
    void findByIdNotFoundTest() {
        UUID fakeId = UUID.randomUUID();
        given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/pharmacy/{id}", fakeId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Обновление несуществующей аптеки возвращает 400")
    @Order(8)
    void updateNotFoundTest() {
        UUID fakeId = UUID.randomUUID();
        PharmacyChain chain = createPharmacyChain();
        CreatePharmacyRq updateRq = createRandomPharmacyRq(chain.getId());
        given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .body(updateRq)
                .when()
                .patch("/api/v1/pharmacy/{id}", fakeId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Удаление несуществующей аптеки возвращает 400")
    @Order(8)
    void deleteNotFoundTest() {
        UUID fakeId = UUID.randomUUID();
        given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/pharmacy/{id}", fakeId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value()); // Проверьте реальное поведение
    }

    @Test
    @DisplayName("Получение информации по несуществующему id возвращает 400")
    @Order(9)
    void getPharmacyInfoNotFoundTest() {
        UUID fakeId = UUID.randomUUID();
        given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/pharmacy/info/{pharmacyId}", fakeId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
