package org.pharmacy.event;

import lombok.Builder;
import org.pharmacy.enums.PharmacyState;

import java.util.UUID;

@Builder
public record PharmacyStateEvent(UUID pharmacyId, String name, PharmacyState state) {
}
