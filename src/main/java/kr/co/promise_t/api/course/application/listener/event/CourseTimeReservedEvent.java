package kr.co.promise_t.api.course.application.listener.event;

import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CourseTimeReservedEvent {
    private CourseTimeId courseTimeId;
    private UserId userId;
}
