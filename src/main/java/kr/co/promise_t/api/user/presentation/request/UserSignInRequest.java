package kr.co.promise_t.api.user.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignInRequest {
    @Email private String email;
    @NotBlank private String password;
}
