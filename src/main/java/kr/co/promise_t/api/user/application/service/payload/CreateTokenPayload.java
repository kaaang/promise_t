package kr.co.promise_t.api.user.application.service.payload;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateTokenPayload {
    private String email;
    private String password;
}
