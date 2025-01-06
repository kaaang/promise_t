package kr.co.promise_t.api.course.application.service;

import jakarta.annotation.Nonnull;
import kr.co.promise_t.api.course.application.service.vo.ReservationStatus;
import kr.co.promise_t.core.course.repository.read.CourseTimeReservationReadRepository;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseTimeService {
    private final CourseTimeCacheService courseTimeCacheService;
    private final CourseTimeReservationReadRepository courseTimeReservationReadRepository;

    public ReservationStatus getCourserTimeReservationStatus(
            @Nonnull CourseTimeId id, @Nonnull UserId userId) {
        return courseTimeCacheService.getCourserTimeReservationStatus(id, userId).orElse(null);
    }
}
