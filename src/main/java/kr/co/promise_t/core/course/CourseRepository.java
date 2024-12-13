package kr.co.promise_t.core.course;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, CourseId> {
    Optional<Course> findByIdAndUserId(@Nonnull CourseId id, @Nonnull UserId userId);

    @Modifying
    @Query("DELETE FROM CourseTimeReservation ctr WHERE ctr.courseTime.id = :courseTimeId")
    void deleteAllCourseTimeReservationByCourseTimeId(@Param("courseTimeId") UUID courseTimeId);
}
