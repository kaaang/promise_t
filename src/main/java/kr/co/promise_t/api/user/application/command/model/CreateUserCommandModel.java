package kr.co.promise_t.api.user.application.command.model;

import kr.co.promise_t.api.kernel.command.CommandModel;
import kr.co.promise_t.core.user.vo.UserRoleType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserCommandModel implements CommandModel {
    private String email;
    private String username;
    private String password;
    private UserRoleType roleType;
}
