package kr.co.promise_t.api.course.application.command;

import java.time.LocalDateTime;
import kr.co.promise_t.api.course.application.command.model.DeleteCourseTimeCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseTimeNotFoundException;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.repository.write.CourseTimeWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCourseTimeCommand implements Command<DeleteCourseTimeCommandModel> {
    private final CourseTimeWriteRepository courseTimeWriteRepository;

    @Override
    @Transactional
    public void execute(DeleteCourseTimeCommandModel model) {
        var time =
                courseTimeWriteRepository
                        .findByIdAndCreatedBy(model.getId(), model.getUserId())
                        .orElseThrow(() -> new CourseTimeNotFoundException("수업 일정을 찾을 수 없습니다."));

        time.remove(LocalDateTime.now());
    }
}
