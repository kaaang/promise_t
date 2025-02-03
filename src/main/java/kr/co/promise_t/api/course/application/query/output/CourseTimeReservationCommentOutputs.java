package kr.co.promise_t.api.course.application.query.output;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseTimeReservationCommentOutputs {
    private UUID id;
    private String content;
    private List<String> path;
}
