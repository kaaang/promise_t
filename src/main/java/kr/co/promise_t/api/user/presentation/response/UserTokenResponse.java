package kr.co.promise_t.api.user.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTokenResponse {
    private String accessToken;
    private String refreshToken;
    private int expiresIn;
    private int refreshExpiresIn;
}
