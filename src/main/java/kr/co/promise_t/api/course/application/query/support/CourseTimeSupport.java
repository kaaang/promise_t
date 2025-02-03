package kr.co.promise_t.api.course.application.query.support;

import static kr.co.promise_t.core.course.QCourseTime.courseTime;
import static kr.co.promise_t.core.course.QCourseTimeReservation.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kr.co.promise_t.api.course.application.query.field.CourseTimesField;
import kr.co.promise_t.core.course.CourseTime;
import kr.co.promise_t.core.course.CourseTimeReservation;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.CourseTimeId;
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
                        .where(courseTime.courseId.eq(id))
                        .where(courseTime.startTime.lt(endTime).and(courseTime.endTime.gt(startTime)));

        return Optional.ofNullable(query.fetchFirst()).orElse(0L);
    }

    public long countsCourseTimeByStartTimeAndEndTimeAndNotId(
            @Nonnull CourseId id,
            @Nonnull LocalDateTime startTime,
            @Nonnull LocalDateTime endTime,
            @Nonnull CourseTimeId timeId) {
        var query =
                queryFactory
                        .select(courseTime.count())
                        .from(courseTime)
                        .where(courseTime.courseId.eq(id))
                        .where(courseTime.id.ne(timeId))
                        .where(courseTime.startTime.lt(endTime).and(courseTime.endTime.gt(startTime)));

        return Optional.ofNullable(query.fetchFirst()).orElse(0L);
    }

    public List<CourseTime> findAllBy(@Nonnull CourseTimesField field) {
        var query =
                queryFactory
                        .selectFrom(courseTime)
                        .leftJoin(courseTime.reservations)
                        .fetchJoin()
                        .where(courseTime.courseId.eq(field.getCourseId()))
                        .where(
                                courseTime
                                        .startTime
                                        .goe(field.getFrom())
                                        .and(courseTime.endTime.loe(field.getTo())));

        return query.fetch();
    }

    public CourseTime findWithReservationBy(@Nonnull CourseTimeId id, @Nonnull UUID reservationId) {
        return queryFactory
                .selectFrom(courseTime)
                .innerJoin(courseTime.reservations, courseTimeReservation)
                .fetchJoin()
                .where(courseTime.id.eq(id))
                .where(courseTimeReservation.id.eq(reservationId))
                .fetchOne();
    }

    public CourseTimeReservation findReservationWithCommentsBy(@Nonnull UUID reservationId) {
        return queryFactory
                .selectFrom(courseTimeReservation)
                .leftJoin(courseTimeReservation.comments)
                .fetchJoin()
                .where(courseTimeReservation.id.eq(reservationId))
                .fetchOne();
    }
}
