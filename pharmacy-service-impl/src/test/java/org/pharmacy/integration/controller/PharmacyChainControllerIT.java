package org.pharmacy.integration.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.pharmacy.dto.CreatePharmacyChainRq;
import org.pharmacy.dto.PharmacyChainRs;
import org.pharmacy.entity.PharmacyChain;
import org.pharmacy.mapper.PharmacyChainMapper;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.pharmacy.utils.TestData.generateRandomInn;

@DisplayName("Тест сервиса для работы с аптечной сетью")
public class PharmacyChainControllerIT extends AbstractControllerIT {

    @MockitoSpyBean
    private PharmacyChainMapper pharmacyChainMapper;

    // Вспомогательный метод для создания аптечной сети
    private PharmacyChainRs createPharmacyChain(CreatePharmacyChainRq request) {
        return pharmacyChainMapper.toDto(pharmacyChainRepository.save(pharmacyChainMapper.toEntity(request)));
    }

    // Генерация случайного запроса для создания сети
    private CreatePharmacyChainRq randomCreatePharmacyChainRq() {
        return CreatePharmacyChainRq.builder()
                .address("Адрес сети " + UUID.randomUUID())
                .locale(false)
                .shortName("Сеть " + UUID.randomUUID().toString().substring(0, 8))
                .inn(generateRandomInn())
                .juridicalName("Юр. лицо " + UUID.randomUUID())
                .juridicalForm("ООО")
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Создание аптечной сети")
    void createTest() {
        CreatePharmacyChainRq request = randomCreatePharmacyChainRq();

        PharmacyChainRs response = createPharmacyChain(request);

        assertNotNull(response.getId());
        assertEquals(request.getAddress(), response.getAddress());
        assertEquals(request.getLocale(), response.getLocale());
        assertEquals(request.getShortName(), response.getShortName());
        assertEquals(request.getInn(), response.getInn());
        assertEquals(request.getJuridicalName(), response.getJuridicalName());
        assertEquals(request.getJuridicalForm(), response.getJuridicalForm());

        // Проверяем в БД
        Optional<PharmacyChain> chainOpt = pharmacyChainRepository.findById(response.getId());
        assertTrue(chainOpt.isPresent());
        PharmacyChain chain = chainOpt.get();
        assertEquals(request.getAddress(), chain.getAddress());
        assertEquals(request.getLocale(), chain.getLocale());
        assertEquals(request.getShortName(), chain.getShortName());
        assertEquals(request.getInn(), chain.getInn());
        assertEquals(request.getJuridicalName(), chain.getJuridicalName());
        assertEquals(request.getJuridicalForm(), chain.getJuridicalForm());
    }

    @Test
    @Order(2)
    @DisplayName("Поиск сети по id")
    void findByIdTest() {
        CreatePharmacyChainRq request = randomCreatePharmacyChainRq();
        PharmacyChainRs created = createPharmacyChain(request);

        PharmacyChainRs found = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/pharmacy-chain/{id}", created.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PharmacyChainRs.class);

        assertEquals(created.getId(), found.getId());
        assertEquals(created.getAddress(), found.getAddress());
        assertEquals(created.getLocale(), found.getLocale());
        assertEquals(created.getShortName(), found.getShortName());
        assertEquals(created.getInn(), found.getInn());
        assertEquals(created.getJuridicalName(), found.getJuridicalName());
        assertEquals(created.getJuridicalForm(), found.getJuridicalForm());
    }

    @Test
    @Order(3)
    @DisplayName("Поиск всех сетей")
    void findAllTest() {
        for (int i = 0; i < 3; i++) {
            createPharmacyChain(randomCreatePharmacyChainRq());
        }

        List<PharmacyChainRs> all = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/pharmacy-chain")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath().getList(".", PharmacyChainRs.class);

        assertEquals(4, all.size());
    }

    @Test
    @Order(4)
    @DisplayName("Обновление сети")
    void updateTest() {
        CreatePharmacyChainRq createRequest = randomCreatePharmacyChainRq();
        PharmacyChainRs created = createPharmacyChain(createRequest);

        // Новые данные для обновления
        CreatePharmacyChainRq updateRequest = CreatePharmacyChainRq.builder()
                .address("Новый адрес " + UUID.randomUUID())
                .locale(true)
                .shortName("Новое короткое имя")
                .inn(generateRandomInn())
                .juridicalName("Новое юр. лицо")
                .juridicalForm("АО")
                .build();

        PharmacyChainRs updated = given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .patch("/api/v1/pharmacy-chain/{id}", created.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PharmacyChainRs.class);

        assertEquals(created.getId(), updated.getId());
        assertEquals(updateRequest.getAddress(), updated.getAddress());
        assertEquals(updateRequest.getLocale(), updated.getLocale());
        assertEquals(updateRequest.getShortName(), updated.getShortName());
        assertEquals(updateRequest.getInn(), updated.getInn());
        assertEquals(updateRequest.getJuridicalName(), updated.getJuridicalName());
        assertEquals(updateRequest.getJuridicalForm(), updated.getJuridicalForm());

        // Проверяем в БД
        Optional<PharmacyChain> chainOpt = pharmacyChainRepository.findById(created.getId());
        assertTrue(chainOpt.isPresent());
        PharmacyChain chain = chainOpt.get();
        assertEquals(updateRequest.getAddress(), chain.getAddress());
        assertEquals(updateRequest.getLocale(), chain.getLocale());
        assertEquals(updateRequest.getShortName(), chain.getShortName());
        assertEquals(updateRequest.getInn(), chain.getInn());
        assertEquals(updateRequest.getJuridicalName(), chain.getJuridicalName());
        assertEquals(updateRequest.getJuridicalForm(), chain.getJuridicalForm());
    }

    @Test
    @Order(5)
    @DisplayName("Удаление сети")
    void deleteTest() {
        CreatePharmacyChainRq request = randomCreatePharmacyChainRq();
        PharmacyChainRs created = createPharmacyChain(request);

        given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/pharmacy-chain/{id}", created.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Проверяем, что в БД нет
        Optional<PharmacyChain> chainOpt = pharmacyChainRepository.findById(created.getId());
        assertFalse(chainOpt.isPresent());
    }

    @Test
    @Order(6)
    @DisplayName("Поиск по несуществующему id возвращает 404")
    void findByIdNotFoundTest() {
        UUID fakeId = UUID.randomUUID();
        given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/pharmacy-chain/{id}", fakeId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(7)
    @DisplayName("Обновление несуществующей сети возвращает 400")
    void updateNotFoundTest() {
        UUID fakeId = UUID.randomUUID();
        CreatePharmacyChainRq updateRequest = randomCreatePharmacyChainRq();
        given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .patch("/api/v1/pharmacy-chain/{id}", fakeId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(8)
    @DisplayName("Удаление несуществующей сети возвращает 400")
    void deleteNotFoundTest() {
        UUID fakeId = UUID.randomUUID();
        given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/pharmacy-chain/{id}", fakeId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(9)
    @DisplayName("Создание сети с дубликатом INN вызывает ошибку")
    void createDuplicateInnTest() {
        CreatePharmacyChainRq request = randomCreatePharmacyChainRq();
        createPharmacyChain(request); // первая сеть

        // Пытаемся создать вторую с тем же INN
        CreatePharmacyChainRq duplicateRequest = CreatePharmacyChainRq.builder()
                .address("Другой адрес")
                .locale(false)
                .shortName("Другая сеть")
                .inn(request.getInn()) // тот же INN
                .juridicalName("Другое юр. лицо")
                .juridicalForm("ИП")
                .build();

        given()
                .header("Content-Type", "CustomHeader")
                .contentType(ContentType.JSON)
                .body(duplicateRequest)
                .when()
                .post("/api/v1/pharmacy-chain")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        // Проверяем, что в БД только одна запись с таким INN
        long count = pharmacyChainRepository.findAll().stream()
                .filter(c -> c.getInn().equals(request.getInn()))
                .count();
        assertEquals(1, count);
    }
}
