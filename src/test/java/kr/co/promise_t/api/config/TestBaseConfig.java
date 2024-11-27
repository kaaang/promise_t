package kr.co.promise_t.api.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import kr.co.promise_t.core.user.User;
import kr.co.promise_t.core.user.UserData;
import kr.co.promise_t.core.user.UserFactory;
import kr.co.promise_t.core.user.vo.UserId;
import kr.co.promise_t.core.user.vo.UserRoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Disabled
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import({
    TestPostgresContainerConfig.class,
    TestRedisConfig.class,
})
@ActiveProfiles(value = "test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class TestBaseConfig {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;

    protected User teacher;
    protected User student;

    @BeforeEach
    void setUp() {
        this.teacher =
                new UserFactory(
                                UserData.builder()
                                        .id(UserId.of(UUID.randomUUID()))
                                        .email("teacher@gmail.com")
                                        .name("teacher")
                                        .password("test")
                                        .roleType(UserRoleType.ROLE_TEACHER)
                                        .build())
                        .create();

        this.student =
                new UserFactory(
                                UserData.builder()
                                        .id(UserId.of(UUID.randomUUID()))
                                        .email("student@gmail.com")
                                        .name("student")
                                        .password("test")
                                        .roleType(UserRoleType.ROLE_STUDENT)
                                        .build())
                        .create();
    }

    protected <T> ResultActions getPostResultActionsBy(
            String path, UserDetails userDetails, T request) throws Exception {
        return mockMvc
                .perform(
                        post(path)
                                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                                .content(objectMapper.writeValueAsString(request))
                                .with(user(userDetails)))
                .andDo(print());
    }
}
