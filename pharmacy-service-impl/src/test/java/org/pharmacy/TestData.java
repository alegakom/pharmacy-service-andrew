package org.pharmacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.pharmacy.dto.TokenInfoDto;
import org.pharmacy.dto.UserInfoDto;

import java.time.LocalDate;

public class TestData {

    public static final String USER_INN = "123456789012";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    public static final String USER_INFO_PATH = "stub/user-info.json";
    public static final String TOKEN_INFO_PATH = "stub/token-info.json";

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
}
