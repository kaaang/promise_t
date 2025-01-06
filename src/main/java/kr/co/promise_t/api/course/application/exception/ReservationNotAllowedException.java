package kr.co.promise_t.api.course.application.exception;

import kr.co.promise_t.api.kernel.exception.BadRequestException;

public class ReservationNotAllowedException extends BadRequestException {
    public ReservationNotAllowedException(String message) {
        super(message);
    }
}
