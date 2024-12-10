package kr.co.promise_t.core.course.vo;

import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.UUID;
import kr.co.promise_t.core.course.Course;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CourseTimeData {
    @Nonnull private UUID id;
    @Nonnull private LocalDateTime startTime;
    @Nonnull private LocalDateTime endTime;
    @Nonnull private Course course;
    private int maxCapacity;
}
