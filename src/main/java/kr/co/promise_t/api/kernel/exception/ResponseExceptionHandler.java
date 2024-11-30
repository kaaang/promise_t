package kr.co.promise_t.api.kernel.exception;

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
                ErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .errors(List.of(e.getMessage()))
                        .path(request.getRequest().getRequestURI())
                        .build();

        return this.toResponseEntity(errorResponse);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> badRequestException(
            BadRequestException e, ServletWebRequest request) {
        var errorResponse =
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(List.of(e.getMessage()))
                        .path(request.getRequest().getRequestURI())
                        .build();

        return this.toResponseEntity(errorResponse);
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
                ErrorResponse.builder()
                        .status(status)
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .errors(errors)
                        .build();

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> accessDeniedException(
            AccessDeniedException e, ServletWebRequest request) {
        var errorResponse =
                ErrorResponse.builder()
                        .status(HttpStatus.FORBIDDEN)
                        .errors(List.of(e.getMessage()))
                        .path(request.getRequest().getRequestURI())
                        .build();

        return this.toResponseEntity(errorResponse);
    }
}
