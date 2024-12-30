package kr.co.promise_t.api.course.application.command;

import jakarta.transaction.Transactional;
import kr.co.promise_t.api.course.application.command.model.UpdateCourseTimeCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseTimeNotFoundException;
import kr.co.promise_t.api.course.application.exception.DuplicatedCourseTimeException;
import kr.co.promise_t.api.course.application.query.CourseTimeQuery;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.CourseTimeData;
import kr.co.promise_t.core.course.CourseTimeFactory;
import kr.co.promise_t.core.course.CourseTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCourseTimeCommand implements Command<UpdateCourseTimeCommandModel> {
    private final CourseTimeRepository courseTimeRepository;
    private final CourseTimeQuery courseTimeQuery;

    @Override
    @Transactional
    public void execute(UpdateCourseTimeCommandModel model) {
        var courseTime =
                courseTimeRepository
                        .findById(model.getId())
                        .orElseThrow(() -> new CourseTimeNotFoundException("수업 일정을 찾을 수 없습니다."));

        if (!courseTimeQuery.canCreateCourseTime(
                courseTime.getCourseId(), model.getStartTime(), model.getEndTime())) {
            throw new DuplicatedCourseTimeException("중복된 수업 시간이 존재합니다.");
        }

        courseTimeRepository.save(
                new CourseTimeFactory(
                                CourseTimeData.builder()
                                        .id(courseTime.getId())
                                        .courseId(courseTime.getCourseId())
                                        .startTime(model.getStartTime())
                                        .endTime(model.getEndTime())
                                        .maxCapacity(model.getMaxCapacity())
                                        .userId(model.getUserId())
                                        .build())
                        .create());
    }
}
