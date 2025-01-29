package kr.co.promise_t.api.course.application.exception;

import kr.co.promise_t.api.kernel.exception.NotFoundException;

public class CourseTimeReservationNotFoundException extends NotFoundException {
    public CourseTimeReservationNotFoundException(String message) {
        super(message);
    }
}
