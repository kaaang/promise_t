package kr.co.promise_t.core.course.repository.write;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.annotation.Nonnull;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.co.promise_t.core.course.CourseTime;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CourseTimeWriteRepository extends JpaRepository<CourseTime, CourseTimeId> {
    Optional<CourseTime> findByIdAndCreatedBy(@Nonnull CourseTimeId id, @Nonnull UserId userId);

    @Modifying
    @Query("DELETE FROM CourseTimeReservation ctr WHERE ctr.courseTime.id = :courseTimeId")
    void deleteAllCourseTimeReservationByCourseTimeId(
            @Param("courseTimeId") CourseTimeId courseTimeId);

    /**
     * 현재 조회량이 많이 않기 때문에 비관적 락을 사용하여서 동시성을 해결 TODO : SQS, Kafka, Redis 중 하나로 동시성을 해결하여 DB의 부하를 줄여야함
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select ct from CourseTime ct where ct.id=:id")
    Optional<CourseTime> findByIdWithLock(@Param("id") CourseTimeId id);
}
