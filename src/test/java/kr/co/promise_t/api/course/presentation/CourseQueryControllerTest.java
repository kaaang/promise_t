package kr.co.promise_t.api.course.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;
import kr.co.promise_t.api.config.TestBaseConfig;
import kr.co.promise_t.core.course.CourseData;
import kr.co.promise_t.core.course.CourseFactory;
import kr.co.promise_t.core.course.CourseRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CourseQueryControllerTest extends TestBaseConfig {
    @Autowired private CourseRepository courseRepository;

    @Nested
    class GetCourseTest {
        private final CourseId courseId = CourseId.of(UUID.randomUUID());

        @BeforeEach
        void setUp() {
            var course =
                    new CourseFactory(
                                    CourseData.builder()
                                            .courseId(courseId)
                                            .teacherId(UserId.of(teacher.getId().getValue()))
                                            .title("test")
                                            .description("test")
                                            .build())
                            .create();
            courseRepository.save(course);
        }

        @Test
        void shouldThrowNotFound_WhenNotExistsCourse() throws Exception {
            getResultActionsBy("/courses/{id}", UUID.randomUUID(), teacher)
                    .andExpect(status().isNotFound());
        }

        @Test
        void shouldBeReturnOk() throws Exception {
            getResultActionsBy("/courses/{id}", courseId.getValue(), teacher).andExpect(status().isOk());
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
                                            .teacherId(UserId.of(teacher.getId().getValue()))
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

            getResultActionsBy("/courses", teacher).andExpect(status().isOk());
        }

        @Test
        void shouldThrowForbidden_WhenRequestStudent() throws Exception {
            params.add("page", "1");
            params.add("size", "10");

            getResultActionsBy("/courses", student).andExpect(status().isForbidden());
        }
    }
}
