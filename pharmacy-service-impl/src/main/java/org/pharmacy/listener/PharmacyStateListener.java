package org.pharmacy.listener;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.pharmacy.enums.PharmacyState;
import org.pharmacy.event.PharmacyStateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PharmacyStateListener {

    @Async
    @EventListener
    @SneakyThrows
    public void handlePharmacyState(PharmacyStateEvent stateEvent) {
        Thread.sleep(10000);
        log.info("Pharmacy with id: " + stateEvent.pharmacyId() + ", PharmacyState: " + stateEvent.state());
    }
}
