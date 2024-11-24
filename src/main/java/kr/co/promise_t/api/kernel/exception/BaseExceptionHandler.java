package kr.co.promise_t.api.kernel.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public interface BaseExceptionHandler {
    default ResponseEntity<Object> errorResponseToResponseEntity(ErrorResponse error) {
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
