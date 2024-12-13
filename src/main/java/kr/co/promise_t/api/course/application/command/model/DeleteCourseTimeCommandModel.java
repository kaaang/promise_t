package kr.co.promise_t.api.course.application.command.model;

import java.util.UUID;
import kr.co.promise_t.api.kernel.command.CommandModel;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteCourseTimeCommandModel implements CommandModel {
    private CourseId courseId;
    private UUID courseTimeId;
    private UserId teacherId;
}
