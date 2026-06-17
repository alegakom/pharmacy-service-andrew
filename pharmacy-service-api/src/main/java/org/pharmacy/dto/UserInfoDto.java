package org.pharmacy.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class UserInfoDto {
    UUID id;
    String userName;
    LocalDate birthDate;
}
