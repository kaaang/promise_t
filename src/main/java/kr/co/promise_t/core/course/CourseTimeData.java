package kr.co.promise_t.core.course;

import java.time.LocalDateTime;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CourseTimeData {
    private CourseTimeId id;
    private CourseId courseId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int maxCapacity;
    private UserId userId;
}
