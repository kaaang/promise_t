package kr.co.promise_t.api.course.application.command;

import kr.co.promise_t.api.course.application.command.model.UpdateCourseCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseAccessDeniedException;
import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.CourseData;
import kr.co.promise_t.core.course.CourseFactory;
import kr.co.promise_t.core.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCourseCommand implements Command<UpdateCourseCommandModel> {
    private final CourseRepository courseRepository;

    @Override
    public void execute(UpdateCourseCommandModel model) {
        var course =
                courseRepository
                        .findById(model.getCourseId())
                        .orElseThrow(() -> new CourseNotFoundException("수업을 찾을 수 없습니다."));

        if (!course.getTeacherId().equals(model.getTeacherId())) {
            throw new CourseAccessDeniedException("수업을 수정할 권한이 없습니다.");
        }

        courseRepository.save(
                new CourseFactory(
                                CourseData.builder()
                                        .courseId(model.getCourseId())
                                        .teacherId(model.getTeacherId())
                                        .title(model.getTitle())
                                        .description(model.getDescription())
                                        .build())
                        .create());
    }
}
