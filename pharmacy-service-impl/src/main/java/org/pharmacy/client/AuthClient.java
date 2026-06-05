package org.pharmacy.client;

import org.pharmacy.controller.CredentialsController;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "auth-service", url = "${auth.service.url}")
public interface AuthClient extends CredentialsController {
}
