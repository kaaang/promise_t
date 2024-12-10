package kr.co.promise_t.api.course.application.query;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;
import kr.co.promise_t.api.config.UnitTestConfig;
import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.core.course.CourseRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class CourseQueryTest extends UnitTestConfig {
    @InjectMocks private CourseQuery courseQuery;

    @Mock private CourseRepository courseRepository;

    @Nested
    class GetCourseTest {
        @Test
        void shouldThrowCourseNotFoundException_WhenCourseIdNotExists() {
            given(courseRepository.findById(any())).willReturn(Optional.empty());

            assertThatThrownBy(() -> courseQuery.getCourseById(CourseId.of(UUID.randomUUID())))
                    .isInstanceOf(CourseNotFoundException.class);
        }
    }
}
