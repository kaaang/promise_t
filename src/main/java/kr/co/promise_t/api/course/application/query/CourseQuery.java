package kr.co.promise_t.api.course.application.query;

import jakarta.annotation.Nonnull;
import java.util.stream.Collectors;
import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.api.course.application.query.field.CoursesField;
import kr.co.promise_t.api.course.application.query.output.CourseOutput;
import kr.co.promise_t.api.course.application.query.output.CourseOutputs;
import kr.co.promise_t.api.course.application.query.support.CourseSupport;
import kr.co.promise_t.core.course.repository.read.CourseReadRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseQuery {
    private final CourseReadRepository courseReadRepository;
    private final CourseSupport courseSupport;

    @Transactional(readOnly = true)
    public CourseOutput getCourseById(@Nonnull CourseId id) {
        var course =
                courseReadRepository
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

    @Transactional(readOnly = true)
    public Page<CourseOutputs> getCoursesBy(@Nonnull CoursesField field) {
        var ids = courseSupport.findIdsBy(field);
        var courses = courseSupport.findByIds(ids);
        var count = courseSupport.countBy(field);

        var outputs =
                courses.stream()
                        .map(
                                course ->
                                        CourseOutputs.builder()
                                                .id(course.getId().getValue())
                                                .title(course.getTitle())
                                                .createdAt(course.getCreatedAt())
                                                .updatedAt(course.getUpdatedAt())
                                                .build())
                        .collect(Collectors.toList());

        return new PageImpl<>(outputs, field.getPageable(), count);
    }
}
