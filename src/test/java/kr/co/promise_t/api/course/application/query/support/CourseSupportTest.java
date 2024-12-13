package kr.co.promise_t.api.course.application.query.support;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;
import kr.co.promise_t.api.config.QueryDslTestConfig;
import kr.co.promise_t.api.course.application.query.field.CoursesField;
import kr.co.promise_t.core.course.vo.CourseId;
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

    @Nested
    @Sql(scripts = {"classpath:/test/course/find-by-id-and-course-time-id.sql"})
    class FindByIdAndCourseTimeIdTest {
        private static final CourseId COURSE_ID =
                CourseId.of(UUID.fromString("e575f1a4-3ddb-4deb-b06b-abb88a2aba39"));
        private static final UUID COURSE_TIME_ID =
                UUID.fromString("4e689a2c-d9e6-4d5c-b54c-781ec9aa3ec9");

        @Test
        void shouldBeReturnEmpty_WhenRequestRandomUUID() {
            var result = courseSupport.findByIdAndCourseTimeId(COURSE_ID, UUID.randomUUID());

            assertThat(result).isEmpty();
        }

        @Test
        void shouldBeReturnCourse() {
            var result = courseSupport.findByIdAndCourseTimeId(COURSE_ID, COURSE_TIME_ID);

            assertThat(result).isNotEmpty();
        }
    }
}
