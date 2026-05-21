package org.pharmacy.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.pharmacy.exception.DuplicateDataException;
import org.pharmacy.exception.NotFoundCrmException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundCrmException.class)
    public String handleNotFoundCrmException(NotFoundCrmException exception) {
        log.error("Not found exception: {}", exception.getMessage());
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateDataException.class)
    public String handleDuplicateDataException(DuplicateDataException exception) {
        log.error("Duplicate data exception: {}", exception.getMessage());
        return exception.getMessage();
    }
}
