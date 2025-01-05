package kr.co.promise_t.api.course.application.service;

import java.time.Duration;
import java.time.LocalDateTime;
import kr.co.promise_t.api.kernel.infrastructure.KeyValueService;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseTimeService {
    private final KeyValueService keyValueService;

    private static final String COURSE_TIME_CAPACITY_KEY = "COURSE_TIME_CAPACITY:%s";

    public void storeCourseTimeReservedCount(CourseTimeId id, LocalDateTime endTime) {
        var key = String.format(COURSE_TIME_CAPACITY_KEY, id.getValue());
        var duration = Duration.between(LocalDateTime.now(), endTime);

        keyValueService.set(key, 0, duration);
    }
}
