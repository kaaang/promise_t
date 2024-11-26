package kr.co.promise_t.api.user.application.exception;

import kr.co.promise_t.api.kernel.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
