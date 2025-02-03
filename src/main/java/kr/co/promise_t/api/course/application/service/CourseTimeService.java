package kr.co.promise_t.api.course.application.service;

import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.UUID;
import kr.co.promise_t.api.course.application.query.CourseTimeQuery;
import kr.co.promise_t.api.course.application.query.output.CourseTimeReservationCommentOutputs;
import kr.co.promise_t.api.course.application.service.vo.ReservationStatus;
import kr.co.promise_t.api.kernel.util.storage.Storage;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseTimeService {
    private final CourseTimeCacheService courseTimeCacheService;
    private final CourseTimeQuery courseTimeQuery;
    private final Storage storage;

    public ReservationStatus getCourserTimeReservationStatus(
            @Nonnull CourseTimeId id, @Nonnull UserId userId) {
        return courseTimeCacheService.getCourserTimeReservationStatus(id, userId).orElse(null);
    }

    public List<CourseTimeReservationCommentOutputs> getCommentsByReservationId(
            @Nonnull UUID reservationId) {
        var reservation = courseTimeQuery.getReservationWithComments(reservationId);

        return reservation.getComments().stream()
                .map(
                        comment ->
                                CourseTimeReservationCommentOutputs.builder()
                                        .id(comment.getId())
                                        .content(comment.getContents())
                                        .path(
                                                comment.getAttachments().stream()
                                                        .map(attachment -> storage.getUri(attachment.getPath()).toString())
                                                        .toList())
                                        .build())
                .toList();
    }
}
