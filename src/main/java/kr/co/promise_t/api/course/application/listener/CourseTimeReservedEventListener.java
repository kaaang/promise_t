package kr.co.promise_t.api.course.application.listener;

import jakarta.transaction.Transactional;
import java.util.UUID;
import kr.co.promise_t.api.course.application.exception.CourseTimeNotFoundException;
import kr.co.promise_t.api.course.application.listener.event.CourseTimeReservedEvent;
import kr.co.promise_t.api.course.application.service.CourseTimeCacheService;
import kr.co.promise_t.api.course.application.service.vo.ReservationStatus;
import kr.co.promise_t.core.course.CourseTimeReservation;
import kr.co.promise_t.core.course.repository.write.CourseTimeWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseTimeReservedEventListener {
    private final CourseTimeWriteRepository courseTimeWriteRepository;
    private final CourseTimeCacheService courseTimeCacheService;

    @KafkaListener(
            topics = "${topic.course-time.reserved.topic}",
            groupId = "${topic.course-time.reserved.status-group}")
    @Transactional
    public void handleCourseTimeReservedEventForStatus(CourseTimeReservedEvent event) {
        var time =
                courseTimeWriteRepository
                        .findById(event.getCourseTimeId())
                        .orElseThrow(() -> new CourseTimeNotFoundException("수업 일정을 찾을 수 없습니다."));

        var currentReservations = courseTimeCacheService.incrementCourseTimeReservedCount(time.getId());
        if (currentReservations > time.getMaxCapacity()) {
            courseTimeCacheService.storeCourseTimeReservationStatus(
                    time.getId(), event.getUserId(), ReservationStatus.FAILED);
            courseTimeCacheService.decrementCourseTimeReservedCount(time.getId());
        } else {
            courseTimeCacheService.storeCourseTimeReservationStatus(
                    time.getId(), event.getUserId(), ReservationStatus.SUCCESS);
            var reservation = CourseTimeReservation.create(UUID.randomUUID(), time, event.getUserId());
            time.addReservation(reservation);
            courseTimeWriteRepository.save(time);
        }
    }
}
