package kr.co.promise_t.api.course.application.exception;

import kr.co.promise_t.api.kernel.exception.AccessDeniedException;

public class CourseAccessDeniedException extends AccessDeniedException {
    public CourseAccessDeniedException(String message) {
        super(message);
    }
}
