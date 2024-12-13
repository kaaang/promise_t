package kr.co.promise_t.api.course.application.exception;

import kr.co.promise_t.api.kernel.exception.NotFoundException;

public class CourseTimeNotFoundException extends NotFoundException {
    public CourseTimeNotFoundException(String message) {
        super(message);
    }
}
