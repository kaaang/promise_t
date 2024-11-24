package kr.co.promise_t.api.user.application.command;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import kr.co.promise_t.api.config.UnitTestConfig;
import kr.co.promise_t.api.user.application.command.model.CreateUserCommandModel;
import kr.co.promise_t.api.user.application.exception.UserAlreadyExistsException;
import kr.co.promise_t.core.user.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class CreateUserCommandTest extends UnitTestConfig {
    @InjectMocks private CreateUserCommand command;

    @Mock private CreateUserCommandModel model;
    @Mock private UserRepository userRepository;

    @Test
    void shouldBeThrowUserAlreadyExistsException_WhenRepositoryReturnUser() {
        given(userRepository.findByEmail(any())).willReturn(Optional.of(mock(User.class)));

        assertThatThrownBy(() -> command.execute(model)).isInstanceOf(UserAlreadyExistsException.class);
    }
}
