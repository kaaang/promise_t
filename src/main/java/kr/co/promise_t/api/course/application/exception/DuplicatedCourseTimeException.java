package kr.co.promise_t.api.course.application.exception;

import kr.co.promise_t.api.kernel.exception.BadRequestException;

public class DuplicatedCourseTimeException extends BadRequestException {
    public DuplicatedCourseTimeException(String message) {
        super(message);
    }
}
