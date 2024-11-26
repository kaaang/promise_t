package kr.co.promise_t.core.user;

import kr.co.promise_t.core.kernel.domain.AbstractDomainFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFactory implements AbstractDomainFactory<User> {
    private final UserData data;

    @Override
    public User create() {
        return User.builder()
                .id(data.getId())
                .email(data.getEmail())
                .name(data.getName())
                .password(data.getPassword())
                .roleType(data.getRoleType())
                .build();
    }
}
