package kr.co.promise_t.api.course.presentation;

import java.util.UUID;
import kr.co.promise_t.api.course.application.query.CourseTimeQuery;
import kr.co.promise_t.api.course.application.query.field.CourseTimesField;
import kr.co.promise_t.api.course.presentation.request.CourseTimesRequest;
import kr.co.promise_t.api.kernel.presentation.http.response.HttpApiResponse;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseTimeQueryController {
    private final CourseTimeQuery courseTimeQuery;

    @GetMapping(value = "/{id}/times")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_ALL_ROLES)")
    public ResponseEntity<Object> getCourseTimes(@PathVariable UUID id, CourseTimesRequest request) {
        var outputs =
                courseTimeQuery.getCourseTimes(
                        CourseTimesField.builder()
                                .courseId(CourseId.of(id))
                                .from(request.getFrom())
                                .to(request.getTo())
                                .build());

        return ResponseEntity.ok(HttpApiResponse.of(outputs));
    }

    @GetMapping(value = "/-/times/{id}")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_ALL_ROLES)")
    public ResponseEntity<Object> getCourseTime(@PathVariable UUID id) {
        var output = courseTimeQuery.getCourseTime(CourseTimeId.of(id));

        return ResponseEntity.ok(HttpApiResponse.of(output));
    }
}
