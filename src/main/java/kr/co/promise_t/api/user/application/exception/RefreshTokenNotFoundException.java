package kr.co.promise_t.api.user.application.exception;

import kr.co.promise_t.api.kernel.exception.NotFoundException;

public class RefreshTokenNotFoundException extends NotFoundException {
    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
