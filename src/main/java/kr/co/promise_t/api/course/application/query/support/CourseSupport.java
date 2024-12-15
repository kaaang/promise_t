package kr.co.promise_t.api.course.application.query.support;

import static kr.co.promise_t.core.course.QCourse.*;
import static kr.co.promise_t.core.course.QCourseTime.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import kr.co.promise_t.api.course.application.query.field.CoursesField;
import kr.co.promise_t.core.course.Course;
import kr.co.promise_t.core.course.vo.CourseId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourseSupport {
    private final JPAQueryFactory queryFactory;

    public List<CourseId> findIdsBy(@Nonnull CoursesField field) {
        var query =
                queryFactory
                        .select(course.id)
                        .from(course)
                        .where(course.createdBy.eq(field.getTeacherId()))
                        .orderBy(course.createdAt.desc())
                        .offset(field.getPageable().getOffset())
                        .limit(field.getPageable().getPageSize());
        return query.fetch();
    }

    public long countBy(@Nonnull CoursesField field) {
        var query =
                queryFactory
                        .select(course.count())
                        .from(course)
                        .where(course.createdBy.eq(field.getTeacherId()));
        return Optional.ofNullable(query.fetchFirst()).orElse(0L);
    }

    public List<Course> findByIds(@Nonnull List<CourseId> ids) {
        return queryFactory
                .selectFrom(course)
                .where(course.id.in(ids))
                .orderBy(course.createdAt.desc())
                .fetch();
    }
}
