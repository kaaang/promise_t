package kr.co.promise_t.api.course.application.command.model;

import java.time.LocalDateTime;
import kr.co.promise_t.api.kernel.command.CommandModel;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateCourseTimeCommandModel implements CommandModel {
    private CourseTimeId id;
    private CourseId courseId;
    private UserId userId;
    private int maxCapacity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
