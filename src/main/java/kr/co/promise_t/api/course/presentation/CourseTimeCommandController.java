package kr.co.promise_t.api.course.presentation;

import jakarta.validation.Valid;
import java.util.UUID;
import kr.co.promise_t.api.course.application.command.*;
import kr.co.promise_t.api.course.application.command.model.*;
import kr.co.promise_t.api.course.presentation.request.CourseTimeCommentCreateRequest;
import kr.co.promise_t.api.course.presentation.request.CourseTimeCreateRequest;
import kr.co.promise_t.api.course.presentation.request.CourseTimeUpdateRequest;
import kr.co.promise_t.api.kernel.command.CommandExecutor;
import kr.co.promise_t.api.kernel.presentation.http.response.HttpApiResponse;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import kr.co.promise_t.core.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/courses")
public class CourseTimeCommandController {
    private final CreateCourseTimeCommand createCourseTimeCommand;
    private final UpdateCourseTimeCommand updateCourseTimeCommand;
    private final DeleteCourseTimeCommand deleteCourseTimeCommand;

    private final CreateReservationCommand createReservationCommand;

    private final CreateCourseTimeCommentCommand createCourseTimeCommentCommand;

    @PostMapping("/{id}/times")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_TEACHER)")
    public ResponseEntity<Object> createCourseTimes(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id,
            @Valid @RequestBody CourseTimeCreateRequest request) {
        var courseTimeId = CourseTimeId.of(UUID.randomUUID());
        new CommandExecutor<>(
                        createCourseTimeCommand,
                        CreateCourseTimeCommandModel.builder()
                                .id(courseTimeId)
                                .courseId(CourseId.of(id))
                                .userId(UserId.of(user.getId().getValue()))
                                .maxCapacity(request.getMaxCapacity())
                                .startTime(request.getStartTime())
                                .endTime(request.getEndTime())
                                .build())
                .invoke();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(HttpApiResponse.of(courseTimeId.getValue()));
    }

    @DeleteMapping("/-/times/{id}")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_TEACHER)")
    public ResponseEntity<Object> deleteCourseTime(
            @AuthenticationPrincipal User user, @PathVariable UUID id) {
        new CommandExecutor<>(
                        deleteCourseTimeCommand,
                        DeleteCourseTimeCommandModel.builder()
                                .id(CourseTimeId.of(id))
                                .userId(UserId.of(user.getId().getValue()))
                                .build())
                .invoke();

        return ResponseEntity.ok().build();
    }

    @PutMapping("/-/times/{id}")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_TEACHER)")
    public ResponseEntity<Object> updateCourseTime(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id,
            @Valid @RequestBody CourseTimeUpdateRequest request) {
        new CommandExecutor<>(
                        updateCourseTimeCommand,
                        UpdateCourseTimeCommandModel.builder()
                                .userId(UserId.of(user.getId().getValue()))
                                .id(CourseTimeId.of(id))
                                .maxCapacity(request.getMaxCapacity())
                                .startTime(request.getStartTime())
                                .endTime(request.getEndTime())
                                .build())
                .invoke();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/-/times/{id}/reservations")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_STUDENT)")
    public ResponseEntity<Object> reservations(
            @AuthenticationPrincipal User user, @PathVariable UUID id) {
        var reservationId = UUID.randomUUID();
        new CommandExecutor<>(
                        createReservationCommand,
                        CreateReservationCommandModel.builder()
                                .id(CourseTimeId.of(id))
                                .userId(UserId.of(user.getId().getValue()))
                                .reservationId(reservationId)
                                .build())
                .invoke();

        return ResponseEntity.status(HttpStatus.CREATED).body(HttpApiResponse.of(reservationId));
    }

    @PostMapping(
            value = "/-/times/{id}/reservations/{reservationId}/comments",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_ALL_ROLES)")
    public ResponseEntity<Object> createComments(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id,
            @PathVariable UUID reservationId,
            @RequestPart CourseTimeCommentCreateRequest request,
            @RequestPart(required = false) MultipartFile file) {

        new CommandExecutor<>(
                        createCourseTimeCommentCommand,
                        CreateCourseTimeCommentCommandModel.builder()
                                .courseTimeId(CourseTimeId.of(id))
                                .reservationId(reservationId)
                                .userid(UserId.of(user.getId().getValue()))
                                .contents(request.getContents())
                                .file(file)
                                .build())
                .invoke();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
