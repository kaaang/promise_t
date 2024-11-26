package kr.co.promise_t.api.user.application.service.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTokenPayload {
    private String accessToken;
    private String refreshToken;
    private int expiresIn;
    private int refreshExpiresIn;
}
