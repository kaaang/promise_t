package kr.co.promise_t.core.course.repository.read;

import jakarta.annotation.Nonnull;
import java.util.Optional;
import kr.co.promise_t.core.course.CourseTime;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTimeReadRepository extends JpaRepository<CourseTime, CourseTimeId> {
    Optional<CourseTime> findByIdAndCreatedBy(@Nonnull CourseTimeId id, @Nonnull UserId userId);
}
