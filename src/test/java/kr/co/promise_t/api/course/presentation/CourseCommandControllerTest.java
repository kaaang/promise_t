package kr.co.promise_t.api.course.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;
import kr.co.promise_t.api.config.TestBaseConfig;
import kr.co.promise_t.api.config.payload.ResultActionsPayload;
import kr.co.promise_t.api.course.presentation.request.CourseCreateRequest;
import kr.co.promise_t.core.course.CourseData;
import kr.co.promise_t.core.course.CourseFactory;
import kr.co.promise_t.core.course.CourseRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

class CourseCommandControllerTest extends TestBaseConfig {
    @Autowired private CourseRepository courseRepository;

    @Nested
    class CreateCourseTest {
        @Test
        void shouldThrowForbidden_WhenRequestStudentUser() throws Exception {
            var request = CourseCreateRequest.builder().title("test").description("test").build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.POST)
                            .path("/courses")
                            .userDetails(student)
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isForbidden());
        }

        @Test
        void shouldBeReturnCreated_WhenRequestTeacherUser() throws Exception {
            var request = CourseCreateRequest.builder().title("test").description("test").build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.POST)
                            .path("/courses")
                            .userDetails(teacher)
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isCreated());
        }
    }

    @Nested
    class DeleteCourseTest {
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
        void shouldThrowCourseNotFoundException_WhenRequestRandomId() throws Exception {
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.DELETE)
                            .path("/courses/{id}")
                            .pathVariable(UUID.randomUUID())
                            .userDetails(teacher)
                            .build();

            getResultActions(payload).andExpect(status().isNotFound());
        }

        @Test
        void shouldThrowCourseAccessDeniedException_WhenRequestOtherTeacher() throws Exception {
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.DELETE)
                            .path("/courses/{id}")
                            .pathVariable(courseId.getValue())
                            .userDetails(otherTeacher)
                            .build();

            getResultActions(payload).andExpect(status().isForbidden());
        }
    }

    @Nested
    class UpdateCourseTest {
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
        void shouldThrowCourseNotFoundException_WhenRequestRandomId() throws Exception {
            var request = CourseCreateRequest.builder().title("test1").description("test1").build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.PUT)
                            .path("/courses/{id}")
                            .pathVariable(UUID.randomUUID())
                            .request(request)
                            .userDetails(teacher)
                            .build();

            getResultActions(payload).andExpect(status().isNotFound());
        }

        @Test
        void shouldThrowCourseAccessDeniedException_WhenRequestOtherTeacher() throws Exception {
            var request = CourseCreateRequest.builder().title("test1").description("test1").build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.PUT)
                            .path("/courses/{id}")
                            .pathVariable(courseId.getValue())
                            .request(request)
                            .userDetails(otherTeacher)
                            .build();

            getResultActions(payload).andExpect(status().isForbidden());
        }

        @Test
        void shouldBeReturnNoContent() throws Exception {
            var request = CourseCreateRequest.builder().title("test1").description("test1").build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.PUT)
                            .path("/courses/{id}")
                            .pathVariable(courseId.getValue())
                            .request(request)
                            .userDetails(teacher)
                            .build();

            getResultActions(payload).andExpect(status().isNoContent());
        }
    }
}
