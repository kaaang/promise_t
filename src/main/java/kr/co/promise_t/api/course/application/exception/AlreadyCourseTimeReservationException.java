package kr.co.promise_t.api.course.application.exception;

import kr.co.promise_t.api.kernel.exception.BadRequestException;

public class AlreadyCourseTimeReservationException extends BadRequestException {
    public AlreadyCourseTimeReservationException(String message) {
        super(message);
    }
}
