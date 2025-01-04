package kr.co.promise_t.api.course.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;
import kr.co.promise_t.api.config.TestBaseConfig;
import kr.co.promise_t.api.config.payload.ResultActionsPayload;
import kr.co.promise_t.core.course.*;
import kr.co.promise_t.core.course.repository.write.CourseTimeWriteRepository;
import kr.co.promise_t.core.course.repository.write.CourseWriteRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

class CourseTimeQueryControllerTest extends TestBaseConfig {
    @Autowired private CourseWriteRepository courseRepository;
    @Autowired private CourseTimeWriteRepository courseTimeRepository;

    @Nested
    class GetCourseTimeTest {
        private final CourseId COURSE_ID = CourseId.of(UUID.randomUUID());
        private final CourseTimeId COURSE_TIME_ID = CourseTimeId.of(UUID.randomUUID());

        @BeforeEach
        void setUp() {
            var course =
                    new CourseFactory(
                                    CourseData.builder()
                                            .courseId(COURSE_ID)
                                            .createdBy(UserId.of(teacher.getId().getValue()))
                                            .title("test")
                                            .description("test")
                                            .build())
                            .create();
            courseRepository.save(course);

            var time =
                    new CourseTimeFactory(
                                    CourseTimeData.builder()
                                            .id(COURSE_TIME_ID)
                                            .courseId(COURSE_ID)
                                            .startTime(LocalDateTime.now().minusHours(1))
                                            .endTime(LocalDateTime.now().plusHours(1))
                                            .maxCapacity(5)
                                            .userId(UserId.of(teacher.getId().getValue()))
                                            .build())
                            .create();
            courseTimeRepository.save(time);
        }

        @Test
        void shouldBeReturnOk() throws Exception {
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.GET)
                            .path("/courses/-/times/{id}")
                            .pathVariable(COURSE_TIME_ID.getValue())
                            .userDetails(teacher)
                            .build();
            getResultActions(payload).andExpect(status().isOk());
        }
    }
}
