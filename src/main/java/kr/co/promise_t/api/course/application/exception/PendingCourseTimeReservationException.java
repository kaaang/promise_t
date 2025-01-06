package kr.co.promise_t.api.course.application.exception;

import kr.co.promise_t.api.kernel.exception.BadRequestException;

public class PendingCourseTimeReservationException extends BadRequestException {
    public PendingCourseTimeReservationException(String message) {
        super(message);
    }
}
