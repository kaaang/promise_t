package kr.co.promise_t.api.course.application.command.model;

import kr.co.promise_t.api.kernel.command.CommandModel;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteCourseTimeCommandModel implements CommandModel {
    private CourseTimeId id;
    private UserId userId;
}
