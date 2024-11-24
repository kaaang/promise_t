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

    public ErrorResponse(HttpStatus status, String errors, String path, LocalDateTime timestamp) {
        this.status = status.value();
        this.errors = Collections.singletonList(errors);
        this.timestamp = timestamp;
        this.path = path;
    }
}
