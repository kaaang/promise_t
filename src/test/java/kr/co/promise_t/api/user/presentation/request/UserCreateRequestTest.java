package kr.co.promise_t.api.user.presentation.request;

import static org.assertj.core.api.Assertions.*;

import kr.co.promise_t.api.config.ValidatorUnitTestConfig;
import kr.co.promise_t.core.user.vo.UserRoleType;
import org.junit.jupiter.api.Test;

class UserCreateRequestTest extends ValidatorUnitTestConfig {
    @Test
    void shouldBeReturnEmptyError() {
        var request = getUserCreateRequestBuilder().build();
        var result = getValidator().validate(request);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldBeReturnError_WhenEmailIsNull() {
        var request = getUserCreateRequestBuilder().email(null).build();
        var result = getValidator().validate(request);

        assertThat(result).isNotEmpty();
    }

    private UserCreateRequest.UserCreateRequestBuilder getUserCreateRequestBuilder() {
        return UserCreateRequest.builder()
                .email("test@gmail.com")
                .password("test")
                .name("test")
                .passwordConfirm("test")
                .roleType(UserRoleType.STUDENT);
    }
}
