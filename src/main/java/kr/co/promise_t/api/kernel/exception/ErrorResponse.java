package kr.co.promise_t.api.kernel.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private List<String> errors;
    private String path;
    private LocalDateTime timestamp;

    public ErrorResponse(HttpStatus status, LocalDateTime timestamp, String path, String error) {
        this.status = status.value();
        this.timestamp = timestamp;
        this.path = path;
        this.errors = Collections.singletonList(error);
    }

    public ErrorResponse(
            HttpStatus status, LocalDateTime timestamp, String path, List<String> errors) {
        this.status = status.value();
        this.timestamp = timestamp;
        this.path = path;
        this.errors = errors;
    }
}
