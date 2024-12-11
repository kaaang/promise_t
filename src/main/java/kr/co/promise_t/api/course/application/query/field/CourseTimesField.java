package kr.co.promise_t.api.course.application.query.field;

import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;
import kr.co.promise_t.core.course.vo.CourseId;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CourseTimesField {
    @Nonnull private CourseId courseId;
    @Nonnull private LocalDateTime from;
    @Nonnull private LocalDateTime to;
}
