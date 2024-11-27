package kr.co.promise_t.core.user;

import kr.co.promise_t.core.user.vo.UserId;
import kr.co.promise_t.core.user.vo.UserRoleType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserData {
    private UserId id;
    private String email;
    private String name;
    private String password;
    private UserRoleType roleType;
}
