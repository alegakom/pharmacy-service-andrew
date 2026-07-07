package org.pharmacy.interceptor.stub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Profile("dev")
@Slf4j
public class StubInterceptor implements ClientHttpRequestInterceptor {

    private static final Map<String, String> STUB_RESPONSE_MAP = Map.of("/external/api/user/info/", "__files/user-info.json");

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("Intercepting the request");

        String requestPath = request.getURI().getPath();
        log.debug("StubInterceptor intercepted request to: {}", requestPath);

        String stubPath = STUB_RESPONSE_MAP.entrySet().stream()
                .filter(entry -> requestPath.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);

        if (stubPath != null) {
            log.info("Returning stub response by path: {}", stubPath);
            ClassPathResource classPathResource = new ClassPathResource(stubPath);
            byte[] response = classPathResource.getContentAsByteArray();
            MockClientHttpResponse mockClientHttpResponse = new MockClientHttpResponse(response, HttpStatus.OK);
            mockClientHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return mockClientHttpResponse;
        }

        log.debug("No stub found for request path: {}. Proceeding with real request.", requestPath);
        return execution.execute(request, body);
    }
}
