package kr.co.promise_t.api.course.application.command;

import jakarta.transaction.Transactional;
import kr.co.promise_t.api.course.application.command.model.CreateCourseTimeCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.api.course.application.exception.DuplicatedCourseTimeException;
import kr.co.promise_t.api.course.application.query.CourseTimeQuery;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCourseTimeCommand implements Command<CreateCourseTimeCommandModel> {
    private final CourseRepository courseRepository;
    private final CourseTimeRepository courseTimeRepository;
    private final CourseTimeQuery courseTimeQuery;

    @Override
    @Transactional
    public void execute(CreateCourseTimeCommandModel model) {
        if (!courseRepository.existsByIdAndCreatedBy(model.getCourseId(), model.getUserId())) {
            throw new CourseNotFoundException("수업을 찾을 수 없습니다.");
        }

        if (!courseTimeQuery.canCreateCourseTime(
                model.getCourseId(), model.getStartTime(), model.getEndTime())) {
            throw new DuplicatedCourseTimeException("중복된 수업 시간이 존재합니다.");
        }

        var courseTime =
                new CourseTimeFactory(
                                CourseTimeData.builder()
                                        .id(model.getId())
                                        .courseId(model.getCourseId())
                                        .startTime(model.getStartTime())
                                        .endTime(model.getEndTime())
                                        .maxCapacity(model.getMaxCapacity())
                                        .userId(model.getUserId())
                                        .build())
                        .create();

        courseTimeRepository.save(courseTime);
    }
}
