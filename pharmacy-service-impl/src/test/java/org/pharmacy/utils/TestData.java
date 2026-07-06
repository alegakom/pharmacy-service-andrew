package org.pharmacy.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.pharmacy.dto.CreatePharmacyRq;
import org.pharmacy.dto.PharmacyInfoDto;
import org.pharmacy.dto.TokenInfoDto;
import org.pharmacy.dto.UserInfoDto;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestData {

    public static final String USER_INN = "123456789012";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    public static final String USER_INFO_PATH = "__files/user-info.json";
    public static final String TOKEN_INFO_PATH = "__files/token-info.json";
    public static final String PHARMACY_INFO_PATH = "__files/pharmacy-info.json";

    @SneakyThrows
    public static <T> T deserialize(String path, Class<T> clazz) {
        return OBJECT_MAPPER.readValue(TestData.class.getClassLoader().getResourceAsStream(path), clazz);
    }

    public static UserInfoDto getUserInfoRs() {
        return deserialize(USER_INFO_PATH, UserInfoDto.class);
    }

    public static TokenInfoDto getTokenInfoRs() {
        TokenInfoDto tokenInfo = deserialize(TOKEN_INFO_PATH, TokenInfoDto.class);
        tokenInfo.setExpiredDate(LocalDate.now().plusDays(1));
        return tokenInfo;
    }

    public static PharmacyInfoDto getPharmacyInfoRs() {
        return deserialize(PHARMACY_INFO_PATH, PharmacyInfoDto.class);
    }

    public static String generateRandomInn() {
        return String.format("%012d", ThreadLocalRandom.current().nextLong(1_000_000_000_00L, 9_999_999_999_99L));
    }

    public static CreatePharmacyRq createRandomPharmacyRq(UUID chainId) {
        return CreatePharmacyRq.builder()
                .address("Тестовый адрес " + UUID.randomUUID())
                .name("Тестовая аптека " + UUID.randomUUID())
                .inn(generateRandomInn())
                .category("A1")
                .juridicalForm("ООО")
                .pharmacyChainId(chainId)
                .build();
    }
}
