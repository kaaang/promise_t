package kr.co.promise_t.api.course.presentation.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseTimeCreateRequest {
    private int maxCapacity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
