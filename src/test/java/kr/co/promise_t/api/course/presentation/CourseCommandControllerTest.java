package kr.co.promise_t.api.course.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import kr.co.promise_t.api.config.TestBaseConfig;
import kr.co.promise_t.api.course.presentation.request.CourseCreateRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CourseCommandControllerTest extends TestBaseConfig {
    @Nested
    class CreateCourseTest {
        @Test
        void shouldThrowForbidden_WhenRequestStudentUser() throws Exception {
            var request = CourseCreateRequest.builder().title("test").description("test").build();

            getPostResultActionsBy("/courses", student, request).andExpect(status().isForbidden());
        }

        @Test
        void shouldBeReturnCreated_WhenRequestTeacherUser() throws Exception {
            var request = CourseCreateRequest.builder().title("test").description("test").build();

            getPostResultActionsBy("/courses", teacher, request).andExpect(status().isCreated());
        }
    }
}
