package kr.co.promise_t.api.course.application.query.support;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;
import kr.co.promise_t.api.config.QueryDslTestConfig;
import kr.co.promise_t.api.course.application.query.field.CoursesField;
import kr.co.promise_t.core.course.vo.UserId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@Import(CourseSupport.class)
class CourseSupportTest extends QueryDslTestConfig {
    @Autowired private CourseSupport courseSupport;

    @Nested
    @Sql(scripts = {"classpath:/test/course/find-ids-by.sql"})
    class FindIdsByTest {
        private static final int courseCount = 2;
        private static final UserId teacherId =
                UserId.of(UUID.fromString("fea6edc4-f92b-49d7-84a6-9fa89b68a53a"));

        @Test
        void shouldBeReturnTwoIds() {
            var field =
                    CoursesField.builder().teacherId(teacherId).pageable(PageRequest.of(0, 10)).build();

            var result = courseSupport.findIdsBy(field);

            assertThat(result.size()).isEqualTo(courseCount);
        }

        @Test
        void shouldBeReturnEmpty_WhenRequestRandomId() {
            var field =
                    CoursesField.builder()
                            .teacherId(UserId.of(UUID.randomUUID()))
                            .pageable(PageRequest.of(0, 10))
                            .build();

            var result = courseSupport.findIdsBy(field);

            assertThat(result).isEmpty();
        }
    }
}
