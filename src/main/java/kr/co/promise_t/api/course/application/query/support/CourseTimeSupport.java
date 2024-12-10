package kr.co.promise_t.api.course.application.query.support;

import static kr.co.promise_t.core.course.QCourseTime.courseTime;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Optional;
import kr.co.promise_t.core.course.vo.CourseId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourseTimeSupport {
    private final JPAQueryFactory queryFactory;

    public long countsCourseTimeByStartTimeAndEndTime(
            @Nonnull CourseId id, @Nonnull LocalDateTime startTime, @Nonnull LocalDateTime endTime) {
        var query =
                queryFactory
                        .select(courseTime.count())
                        .from(courseTime)
                        .where(courseTime.course.id.eq(id))
                        .where(courseTime.startTime.lt(endTime).and(courseTime.endTime.gt(startTime)));

        return Optional.ofNullable(query.fetchFirst()).orElse(0L);
    }
}
