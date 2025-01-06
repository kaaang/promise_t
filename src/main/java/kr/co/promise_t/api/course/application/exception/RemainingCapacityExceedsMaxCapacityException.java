package kr.co.promise_t.api.course.application.exception;

import kr.co.promise_t.api.kernel.exception.BadRequestException;

public class RemainingCapacityExceedsMaxCapacityException extends BadRequestException {
    public RemainingCapacityExceedsMaxCapacityException(String message) {
        super(message);
    }
}
