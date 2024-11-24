package kr.co.promise_t.api.kernel.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler
        implements BaseExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> notFoundException(NotFoundException e, ServletWebRequest request) {
        var errorResponse =
                new ErrorResponse(
                        HttpStatus.NOT_FOUND,
                        e.getMessage(),
                        request.getRequest().getRequestURI(),
                        LocalDateTime.now());

        return this.errorResponseToResponseEntity(errorResponse);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> badRequestException(RuntimeException e, ServletWebRequest request) {
        var errorResponse =
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        e.getMessage(),
                        request.getRequest().getRequestURI(),
                        LocalDateTime.now());

        return this.errorResponseToResponseEntity(errorResponse);
    }
}
