package kr.co.promise_t.api.course.application.command;

import kr.co.promise_t.api.course.application.command.model.CreateCourseTimeCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.api.course.application.exception.DuplicatedCourseTimeException;
import kr.co.promise_t.api.course.application.query.CourseTimeQuery;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.CourseRepository;
import kr.co.promise_t.core.course.CourseTime;
import kr.co.promise_t.core.course.vo.CourseTimeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCourseTimeCommand implements Command<CreateCourseTimeCommandModel> {
    private final CourseRepository courseRepository;
    private final CourseTimeQuery courseTimeQuery;

    @Override
    public void execute(CreateCourseTimeCommandModel model) {
        var course =
                courseRepository
                        .findByIdAndUserId(model.getCourseId(), model.getTeacherId())
                        .orElseThrow(() -> new CourseNotFoundException("수업을 찾을 수 없습니다."));

        if (!courseTimeQuery.canCreateCourseTime(
                course.getId(), model.getStartTime(), model.getEndTime())) {
            throw new DuplicatedCourseTimeException("중복된 수업 시간이 존재합니다.");
        }

        var courseTime =
                CourseTime.create(
                        CourseTimeData.builder()
                                .id(model.getId())
                                .startTime(model.getStartTime())
                                .endTime(model.getEndTime())
                                .maxCapacity(model.getMaxCapacity())
                                .course(course)
                                .build());
        course.addTimes(courseTime);

        courseRepository.save(course);
    }
}
