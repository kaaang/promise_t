package kr.co.promise_t.api.user.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import kr.co.promise_t.core.user.vo.UserRoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "빈 값을 입력할 수 없습니다.")
    @Email(message = "정상적이지 않은 이메일 입니다.")
    private String email;

    @NotBlank(message = "비밀번호를 빈 값일 수 없습니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 빈 값일 수 없습니다.")
    private String passwordConfirm;

    @NotEmpty(message = "유저 타입은 필수 입니다.")
    private UserRoleType roleType;
}
