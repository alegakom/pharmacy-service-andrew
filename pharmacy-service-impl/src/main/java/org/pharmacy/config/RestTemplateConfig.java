package org.pharmacy.config;

import org.pharmacy.interceptor.stub.StubInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(Optional<StubInterceptor> stubInterceptor) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(10))
                .build();

        stubInterceptor.ifPresent(interceptor ->
                restTemplate.setInterceptors(List.of(interceptor)));

        return restTemplate;
    }
}
