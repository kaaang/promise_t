package kr.co.promise_t.api.course.presentation;

import jakarta.validation.Valid;
import java.util.UUID;
import kr.co.promise_t.api.course.application.command.CreateCourseCommand;
import kr.co.promise_t.api.course.application.command.DeleteCourseCommand;
import kr.co.promise_t.api.course.application.command.UpdateCourseCommand;
import kr.co.promise_t.api.course.application.command.model.CreateCourseCommandModel;
import kr.co.promise_t.api.course.application.command.model.DeleteCourseCommandModel;
import kr.co.promise_t.api.course.application.command.model.UpdateCourseCommandModel;
import kr.co.promise_t.api.course.presentation.request.CourseCreateRequest;
import kr.co.promise_t.api.course.presentation.request.CourseUpdateRequest;
import kr.co.promise_t.api.kernel.command.CommandExecutor;
import kr.co.promise_t.api.kernel.presentation.http.response.HttpApiResponse;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import kr.co.promise_t.core.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseCommandController {
    private final CreateCourseCommand createCourseCommand;
    private final DeleteCourseCommand deleteCourseCommand;
    private final UpdateCourseCommand updateCourseCommand;

    @PostMapping
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_TEACHER)")
    public ResponseEntity<Object> create(
            @AuthenticationPrincipal User user, @Valid @RequestBody CourseCreateRequest request) {
        var courseId = CourseId.of(UUID.randomUUID());

        new CommandExecutor<>(
                        createCourseCommand,
                        CreateCourseCommandModel.builder()
                                .courseId(courseId)
                                .teacherId(UserId.of(user.getId().getValue()))
                                .title(request.getTitle())
                                .description(request.getDescription())
                                .build())
                .invoke();

        return ResponseEntity.status(HttpStatus.CREATED).body(HttpApiResponse.of(courseId.getValue()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_TEACHER)")
    public ResponseEntity<Object> delete(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        new CommandExecutor<>(
                        deleteCourseCommand,
                        DeleteCourseCommandModel.builder()
                                .courseId(CourseId.of(id))
                                .teacherId(UserId.of(user.getId().getValue()))
                                .build())
                .invoke();

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_TEACHER)")
    public ResponseEntity<Object> update(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id,
            @Valid @RequestBody CourseUpdateRequest request) {
        new CommandExecutor<>(
                        updateCourseCommand,
                        UpdateCourseCommandModel.builder()
                                .courseId(CourseId.of(id))
                                .teacherId(UserId.of(user.getId().getValue()))
                                .title(request.getTitle())
                                .description(request.getDescription())
                                .build())
                .invoke();

        return ResponseEntity.noContent().build();
    }
}
