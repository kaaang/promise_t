package kr.co.promise_t.core.course;

import kr.co.promise_t.core.kernel.domain.AbstractDomainFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CourseTimeFactory implements AbstractDomainFactory<CourseTime> {
    private final CourseTimeData data;

    @Override
    public CourseTime create() {
        return CourseTime.builder()
                .id(data.getId())
                .courseId(data.getCourseId())
                .startTime(data.getStartTime())
                .endTime(data.getEndTime())
                .maxCapacity(data.getMaxCapacity())
                .createdBy(data.getUserId())
                .build();
    }
}
