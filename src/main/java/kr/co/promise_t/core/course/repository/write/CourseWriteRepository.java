package kr.co.promise_t.core.course.repository.write;

import jakarta.annotation.Nonnull;
import java.util.Optional;
import kr.co.promise_t.core.course.Course;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseWriteRepository extends JpaRepository<Course, CourseId> {
    Optional<Course> findByIdAndCreatedBy(@Nonnull CourseId id, @Nonnull UserId userId);

    boolean existsByIdAndCreatedBy(@Nonnull CourseId id, @Nonnull UserId userId);
}
