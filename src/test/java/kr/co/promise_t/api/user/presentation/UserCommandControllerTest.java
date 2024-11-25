package kr.co.promise_t.api.user.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;
import kr.co.promise_t.api.config.TestBaseConfig;
import kr.co.promise_t.api.user.presentation.request.UserCreateRequest;
import kr.co.promise_t.core.user.UserData;
import kr.co.promise_t.core.user.UserFactory;
import kr.co.promise_t.core.user.UserId;
import kr.co.promise_t.core.user.UserRepository;
import kr.co.promise_t.core.user.vo.UserRoleType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class UserCommandControllerTest extends TestBaseConfig {
    @Autowired private UserRepository userRepository;

    @Nested
    class CreateUserCommandTest {
        @Test
        void shouldBeReturn201() throws Exception {
            var request =
                    UserCreateRequest.builder()
                            .email("test@gmail.com")
                            .password("test")
                            .passwordConfirm("test")
                            .name("test")
                            .roleType(UserRoleType.STUDENT)
                            .build();

            this.getResultActionsBy(request).andExpect(status().isCreated());
        }

        @Test
        void shouldBeReturn400Error_WhenAlreadyEmail() throws Exception {
            userRepository.save(
                    new UserFactory(
                                    UserData.builder()
                                            .id(UserId.of(UUID.randomUUID()))
                                            .email("test@gmail.com")
                                            .password("test")
                                            .name("test")
                                            .roleType(UserRoleType.STUDENT)
                                            .build())
                            .create());

            var request =
                    UserCreateRequest.builder()
                            .email("test@gmail.com")
                            .password("test")
                            .passwordConfirm("test")
                            .name("test")
                            .roleType(UserRoleType.STUDENT)
                            .build();

            this.getResultActionsBy(request).andExpect(status().isBadRequest());
        }

        private ResultActions getResultActionsBy(UserCreateRequest request) throws Exception {
            return mockMvc
                    .perform(
                            post("/users")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                    .andDo(print());
        }
    }
}
