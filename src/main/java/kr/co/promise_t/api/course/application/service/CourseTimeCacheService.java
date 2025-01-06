package kr.co.promise_t.api.course.application.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import kr.co.promise_t.api.course.application.service.vo.ReservationStatus;
import kr.co.promise_t.api.kernel.infrastructure.cache.KeyValueService;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseTimeCacheService {
    private final KeyValueService keyValueService;

    private static final String COURSE_TIME_CAPACITY_KEY = "COURSE_TIME_CAPACITY:%s";
    private static final String COURSE_TIME_RESERVATION_STATUS_KEY =
            "COURSE_TIME_RESERVATION_STATUS:%s:%s";
    private static final Duration COURSE_TIME_RESERVATION_STATUS_DURATION =
            Duration.ofSeconds(60 * 5);

    public void storeCourseTimeReservedCount(CourseTimeId id, LocalDateTime endTime) {
        var key = String.format(COURSE_TIME_CAPACITY_KEY, id.getValue());
        var duration = Duration.between(LocalDateTime.now(), endTime);

        keyValueService.set(key, 0, duration);
    }

    public void setCourseTimeReservedExpire(CourseTimeId id, LocalDateTime endTime) {
        var key = String.format(COURSE_TIME_CAPACITY_KEY, id.getValue());
        if (!keyValueService.exists(key)) {
            return;
        }

        var duration = Duration.between(LocalDateTime.now(), endTime);
        keyValueService.expire(key, duration);
    }

    public long getCourseTimeReservedCount(CourseTimeId id) {
        var key = String.format(COURSE_TIME_CAPACITY_KEY, id.getValue());
        return keyValueService.get(key, Long.class);
    }

    public long incrementCourseTimeReservedCount(CourseTimeId id) {
        var key = String.format(COURSE_TIME_CAPACITY_KEY, id.getValue());
        return keyValueService.increase(key, 1L);
    }

    public void decrementCourseTimeReservedCount(CourseTimeId id) {
        var key = String.format(COURSE_TIME_CAPACITY_KEY, id.getValue());
        keyValueService.decrease(key, 1L);
    }

    public void storeCourseTimeReservationStatus(
            CourseTimeId id, UserId userId, ReservationStatus status) {
        var key = String.format(COURSE_TIME_RESERVATION_STATUS_KEY, id.getValue(), userId.getValue());
        keyValueService.set(key, status.name(), COURSE_TIME_RESERVATION_STATUS_DURATION);
    }

    public Optional<ReservationStatus> getCourserTimeReservationStatus(
            CourseTimeId id, UserId userId) {
        var key = String.format(COURSE_TIME_RESERVATION_STATUS_KEY, id.getValue(), userId.getValue());
        var value = keyValueService.get(key, String.class);

        return Optional.ofNullable(value).map(ReservationStatus::valueOf);
    }
}
