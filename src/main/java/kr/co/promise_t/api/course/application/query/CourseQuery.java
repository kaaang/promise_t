package kr.co.promise_t.api.course.application.query;

import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.api.course.application.query.output.CourseOutput;
import kr.co.promise_t.core.course.CourseRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseQuery {
    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public CourseOutput getCourse(CourseId id) {
        var course =
                courseRepository
                        .findById(id)
                        .orElseThrow(() -> new CourseNotFoundException("수업을 찾을 수 없습니다."));

        return CourseOutput.builder()
                .id(course.getId().getValue())
                .title(course.getTitle())
                .description(course.getDescription())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}
