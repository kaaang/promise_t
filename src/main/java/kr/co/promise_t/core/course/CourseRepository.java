package kr.co.promise_t.core.course;

import kr.co.promise_t.core.course.vo.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, CourseId> {}
