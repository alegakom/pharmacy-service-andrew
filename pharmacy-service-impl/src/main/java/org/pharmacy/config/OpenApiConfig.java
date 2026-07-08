package org.pharmacy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pharmacyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pharmacy Service API")
                        .description("API для управления аптеками и аптечными сетями")
                        .version("1.0"));
    }
}
