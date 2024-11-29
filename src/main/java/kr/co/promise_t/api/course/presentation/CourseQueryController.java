package kr.co.promise_t.api.course.presentation;

import jakarta.annotation.Nonnull;
import java.util.UUID;
import kr.co.promise_t.api.course.application.query.CourseQuery;
import kr.co.promise_t.api.course.application.query.field.CoursesField;
import kr.co.promise_t.api.kernel.page.PageInfo;
import kr.co.promise_t.api.kernel.presentation.http.response.HttpApiResponse;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import kr.co.promise_t.core.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseQueryController {
    private final CourseQuery courseQuery;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_ALL_ROLES)")
    public ResponseEntity<Object> getCourse(@PathVariable UUID id) {
        var output = courseQuery.getCourse(CourseId.of(id));

        return ResponseEntity.ok(HttpApiResponse.of(output));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_TEACHER)")
    public ResponseEntity<Object> getCourses(
            @AuthenticationPrincipal User user, @Nonnull Pageable pageable) {
        var outputs =
                courseQuery.getCourses(
                        CoursesField.builder()
                                .teacherId(UserId.of(user.getId().getValue()))
                                .pageable(pageable)
                                .build());

        return ResponseEntity.ok(
                HttpApiResponse.of(outputs.getContent(), new PageInfo(outputs).toMap()));
    }
}
