package org.pharmacy.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PharmacyInfoDto {
    String director;
    String address;
    String inn;
}
