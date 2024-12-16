package kr.co.promise_t.api.course.application.query.output;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseTimeOutput {
    private UUID id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int maxCapacity;
    private int remainingCapacity;
    private int reservedCount;
}
