package kr.co.promise_t.api.course.application.query;

import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;
import kr.co.promise_t.api.course.application.query.support.CourseTimeSupport;
import kr.co.promise_t.core.course.vo.CourseId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseTimeQuery {
    private final CourseTimeSupport courseTimeSupport;

    @Transactional(readOnly = true)
    public boolean canCreateCourseTime(
            @Nonnull CourseId id, @Nonnull LocalDateTime startTime, @Nonnull LocalDateTime endTime) {
        var count = courseTimeSupport.countsCourseTimeByStartTimeAndEndTime(id, startTime, endTime);

        return count <= 0;
    }
}
