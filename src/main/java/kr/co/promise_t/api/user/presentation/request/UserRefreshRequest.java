package kr.co.promise_t.api.user.presentation.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRefreshRequest {
    @NotBlank private String refreshToken;
}
