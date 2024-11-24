package kr.co.promise_t.api.kernel.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler
        implements BaseExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> notFoundException(NotFoundException e, ServletWebRequest request) {
        var errorResponse =
                new ErrorResponse(
                        HttpStatus.NOT_FOUND,
                        LocalDateTime.now(),
                        request.getRequest().getRequestURI(),
                        e.getMessage());

        return this.errorResponseToResponseEntity(errorResponse);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> badRequestException(
            BadRequestException e, ServletWebRequest request) {
        var errorResponse =
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now(),
                        request.getRequest().getRequestURI(),
                        e.getMessage());

        return this.errorResponseToResponseEntity(errorResponse);
    }

    @Override
    public ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
            logger.info(
                    "== Error Field : "
                            + error.getField()
                            + " , Value : "
                            + error.getRejectedValue()
                            + " , Message : "
                            + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
            logger.info("== Error Message: " + error.getDefaultMessage());
        }

        var errorResponse =
                new ErrorResponse(
                        status,
                        LocalDateTime.now(),
                        ((ServletWebRequest) request).getRequest().getRequestURI(),
                        errors);

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }
}
