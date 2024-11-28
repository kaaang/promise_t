package kr.co.promise_t.api.course.application.exception;

import kr.co.promise_t.api.kernel.exception.NotFoundException;

public class CourseNotFoundException extends NotFoundException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
