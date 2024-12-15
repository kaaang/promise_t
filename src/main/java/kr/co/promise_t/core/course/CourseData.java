package kr.co.promise_t.core.course;

import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CourseData {
    private CourseId courseId;
    private UserId createdBy;
    private String title;
    private String description;
}
