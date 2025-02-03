package kr.co.promise_t.api.course.application.query;

import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kr.co.promise_t.api.course.application.exception.CourseTimeNotFoundException;
import kr.co.promise_t.api.course.application.query.field.CourseTimesField;
import kr.co.promise_t.api.course.application.query.output.CourseTimeOutput;
import kr.co.promise_t.api.course.application.query.output.CourseTimeOutputs;
import kr.co.promise_t.api.course.application.query.support.CourseTimeSupport;
import kr.co.promise_t.core.course.CourseTime;
import kr.co.promise_t.core.course.CourseTimeReservation;
import kr.co.promise_t.core.course.repository.read.CourseTimeReadRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseTimeQuery {
    private final CourseTimeReadRepository courseTimeReadRepository;
    private final CourseTimeSupport courseTimeSupport;

    @Transactional(readOnly = true)
    public boolean canCreateCourseTime(
            @Nonnull CourseId id, @Nonnull LocalDateTime startTime, @Nonnull LocalDateTime endTime) {
        var count = courseTimeSupport.countsCourseTimeByStartTimeAndEndTime(id, startTime, endTime);

        return count <= 0;
    }

    @Transactional(readOnly = true)
    public boolean canUpdateCourseTime(
            @Nonnull CourseId id,
            @Nonnull LocalDateTime startTime,
            @Nonnull LocalDateTime endTime,
            @Nonnull CourseTimeId courseTimeId) {
        var count =
                courseTimeSupport.countsCourseTimeByStartTimeAndEndTimeAndNotId(
                        id, startTime, endTime, courseTimeId);

        return count <= 0;
    }

    @Transactional(readOnly = true)
    public List<CourseTimeOutputs> getCourseTimes(@Nonnull CourseTimesField field) {
        var courseTimes = courseTimeSupport.findAllBy(field);

        return courseTimes.stream()
                .map(
                        time ->
                                CourseTimeOutputs.builder()
                                        .id(time.getId().getValue())
                                        .startTime(time.getStartTime())
                                        .endTime(time.getEndTime())
                                        .maxCapacity(time.getMaxCapacity())
                                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseTimeOutput getCourseTime(@Nonnull CourseTimeId id) {
        var time =
                courseTimeReadRepository
                        .findById(id)
                        .orElseThrow(() -> new CourseTimeNotFoundException("수업 일정을 찾을 수 없습니다."));

        return CourseTimeOutput.builder()
                .id(time.getId().getValue())
                .startTime(time.getStartTime())
                .endTime(time.getEndTime())
                .maxCapacity(time.getMaxCapacity())
                .build();
    }

    @Transactional(readOnly = true)
    public Optional<CourseTime> getCourseTimeWithReservationBy(
            @Nonnull CourseTimeId id, @Nonnull UUID reservationId) {
        return Optional.ofNullable(courseTimeSupport.findWithReservationBy(id, reservationId));
    }

    @Transactional(readOnly = true)
    public CourseTimeReservation getReservationWithComments(@Nonnull UUID reservationId) {
        return courseTimeSupport.findReservationWithCommentsBy(reservationId);
    }
}
