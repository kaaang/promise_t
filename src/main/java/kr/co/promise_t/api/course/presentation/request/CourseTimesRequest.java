package kr.co.promise_t.api.course.presentation.request;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseTimesRequest {
    private LocalDateTime from;
    private LocalDateTime to;
}
