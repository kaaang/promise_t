package kr.co.promise_t.api.kernel.presentation.http.vo;

import java.util.List;
import kr.co.promise_t.core.user.vo.UserRoleType;
import org.springframework.stereotype.Component;

@Component("RoleContainer")
public class RoleContainer {
    public static final List<String> ALLOW_TEACHER = List.of(UserRoleType.ROLE_TEACHER.name());
}