package kr.co.promise_t.core.course.repository.read;

import java.util.Optional;
import java.util.UUID;
import kr.co.promise_t.core.course.CourseTime;
import kr.co.promise_t.core.course.CourseTimeReservation;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTimeReservationReadRepository
        extends JpaRepository<CourseTimeReservation, UUID> {
    Optional<CourseTimeReservation> findByCourseTimeAndUserId(CourseTime courseTime, UserId userId);

    Optional<CourseTimeReservation> findByCourseTimeIdAndUserId(
            CourseTimeId courseTime_id, UserId userId);
}
