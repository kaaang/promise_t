package kr.co.promise_t.api.course.application.query.support;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;
import kr.co.promise_t.api.config.QueryDslTestConfig;
import kr.co.promise_t.api.course.application.query.field.CourseTimesField;
import kr.co.promise_t.core.course.vo.CourseId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@Import(CourseTimeSupport.class)
class CourseTimeSupportTest extends QueryDslTestConfig {
    @Autowired private CourseTimeSupport support;

    @Nested
    @Sql(scripts = {"classpath:/test/course/course_time/find-all-by.sql"})
    class FindAllByTest {
        private static final CourseId COURSE_ID =
                CourseId.of(UUID.fromString("e575f1a4-3ddb-4deb-b06b-abb88a2aba39"));
        private static final LocalDateTime MIN_TIME = LocalDateTime.of(2024, 12, 10, 19, 0, 0);
        private static final LocalDateTime MAX_TIME = LocalDateTime.of(2024, 12, 10, 19, 40, 0);

        @Test
        void shouldBeReturnCourseTimes() {
            var field =
                    CourseTimesField.builder().courseId(COURSE_ID).from(MIN_TIME).to(MAX_TIME).build();

            var results = support.findAllBy(field);

            assertThat(results).isNotEmpty();
        }
    }
}
