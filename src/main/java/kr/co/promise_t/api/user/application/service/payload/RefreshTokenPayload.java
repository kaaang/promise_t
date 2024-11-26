package kr.co.promise_t.api.user.application.service.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenPayload {
    private String refreshToken;
}
