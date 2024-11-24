package kr.co.promise_t.api.user.application.exception;

import kr.co.promise_t.api.kernel.exception.BadRequestException;

public class UserAlreadyExistsException extends BadRequestException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
