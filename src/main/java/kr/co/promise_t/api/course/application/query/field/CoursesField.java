package kr.co.promise_t.api.course.application.query.field;

import kr.co.promise_t.core.course.vo.UserId;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Builder
@Getter
public class CoursesField {
    private UserId teacherId;
    private Pageable pageable;
}
