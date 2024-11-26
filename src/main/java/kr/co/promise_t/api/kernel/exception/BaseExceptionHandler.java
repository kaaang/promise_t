package kr.co.promise_t.api.kernel.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public interface BaseExceptionHandler {
    default ResponseEntity<Object> toResponseEntity(ErrorResponse response) {
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request);
}
