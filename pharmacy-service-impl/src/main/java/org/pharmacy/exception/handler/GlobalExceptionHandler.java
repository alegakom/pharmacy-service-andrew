package org.pharmacy.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.pharmacy.exception.DuplicateInnException;
import org.pharmacy.exception.NotFoundCrmException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Slf4j
public class GlobalExceptionHandler {

    public String handleNotFoundCrmException(NotFoundCrmException exception) {
        log.error("Not found exception: {}", exception.getMessage());
        return exception.getMessage();
    }

    public String handleDuplicateInnException(DuplicateInnException exception) {
        log.error("Duplicate INN exception: {}", exception.getMessage());
        return exception.getMessage();
    }
}
