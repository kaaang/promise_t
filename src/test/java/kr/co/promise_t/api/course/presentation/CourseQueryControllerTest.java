package kr.co.promise_t.api.course.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;
import kr.co.promise_t.api.config.TestBaseConfig;
import kr.co.promise_t.api.config.payload.ResultActionsPayload;
import kr.co.promise_t.core.course.CourseData;
import kr.co.promise_t.core.course.CourseFactory;
import kr.co.promise_t.core.course.repository.write.CourseWriteRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

class CourseQueryControllerTest extends TestBaseConfig {
    @Autowired private CourseWriteRepository courseRepository;

    @Nested
    class GetCourseTest {
        private final CourseId courseId = CourseId.of(UUID.randomUUID());

        @BeforeEach
        void setUp() {
            var course =
                    new CourseFactory(
                                    CourseData.builder()
                                            .courseId(courseId)
                                            .createdBy(UserId.of(teacher.getId().getValue()))
                                            .title("test")
                                            .description("test")
                                            .build())
                            .create();
            courseRepository.save(course);
        }

        @Test
        void shouldThrowNotFound_WhenNotExistsCourse() throws Exception {
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.GET)
                            .path("/courses/{id}")
                            .pathVariable(UUID.randomUUID())
                            .userDetails(teacher)
                            .build();
            getResultActions(payload).andExpect(status().isNotFound());
        }

        @Test
        void shouldBeReturnOk() throws Exception {
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.GET)
                            .path("/courses/{id}")
                            .pathVariable(courseId.getValue())
                            .userDetails(teacher)
                            .build();
            getResultActions(payload).andExpect(status().isOk());
        }
    }

    @Nested
    class GetCoursesTest {
        private final CourseId courseId = CourseId.of(UUID.randomUUID());

        @BeforeEach
        void setUp() {
            var course =
                    new CourseFactory(
                                    CourseData.builder()
                                            .courseId(courseId)
                                            .createdBy(UserId.of(teacher.getId().getValue()))
                                            .title("test")
                                            .description("test")
                                            .build())
                            .create();
            courseRepository.save(course);
        }

        @Test
        void shouldBeReturnOk() throws Exception {
            params.add("page", "1");
            params.add("size", "10");

            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.GET)
                            .path("/courses")
                            .userDetails(teacher)
                            .build();
            getResultActions(payload).andExpect(status().isOk());
        }

        @Test
        void shouldThrowForbidden_WhenRequestStudent() throws Exception {
            params.add("page", "1");
            params.add("size", "10");

            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.GET)
                            .path("/courses")
                            .userDetails(student)
                            .build();
            getResultActions(payload).andExpect(status().isForbidden());
        }
    }
}
