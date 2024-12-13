package kr.co.promise_t.api.course.application.command;

import kr.co.promise_t.api.course.application.command.model.DeleteCourseTimeCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseAccessDeniedException;
import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.api.course.application.exception.CourseTimeNotFoundException;
import kr.co.promise_t.api.course.application.query.support.CourseSupport;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCourseTimeCommand implements Command<DeleteCourseTimeCommandModel> {
    private final CourseRepository courseRepository;
    private final CourseSupport courseSupport;

    @Override
    @Transactional
    public void execute(DeleteCourseTimeCommandModel model) {
        var course =
                courseSupport
                        .findByIdAndCourseTimeId(model.getCourseId(), model.getCourseTimeId())
                        .orElseThrow(() -> new CourseNotFoundException("수업을 찾을 수 없습니다."));

        if (!course.getUserId().equals(model.getTeacherId())) {
            throw new CourseAccessDeniedException("수업일정을 삭제할 권한이 없습니다.");
        }

        var time =
                course.getTimes().stream()
                        .findFirst()
                        .orElseThrow(() -> new CourseTimeNotFoundException("수업 일정을 찾을 수 없습니다."));

        courseRepository.deleteAllCourseTimeReservationByCourseTimeId(time.getId());
        course.removeTimes(time);
    }
}
