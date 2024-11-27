package kr.co.promise_t.core.course;

import kr.co.promise_t.core.kernel.domain.AbstractDomainFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CourseFactory implements AbstractDomainFactory<Course> {
    private final CourseData data;

    @Override
    public Course create() {
        return Course.builder()
                .id(data.getCourseId())
                .teacherId(data.getTeacherId())
                .title(data.getTitle())
                .description(data.getDescription())
                .build();
    }
}
