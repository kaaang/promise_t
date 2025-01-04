package kr.co.promise_t.api.course.application.command;

import java.time.LocalDateTime;
import kr.co.promise_t.api.course.application.command.model.DeleteCourseCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseAccessDeniedException;
import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.repository.write.CourseWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCourseCommand implements Command<DeleteCourseCommandModel> {
    private final CourseWriteRepository courseWriteRepository;

    @Override
    @Transactional
    public void execute(DeleteCourseCommandModel model) {
        var course =
                courseWriteRepository
                        .findById(model.getCourseId())
                        .orElseThrow(() -> new CourseNotFoundException("수업을 찾을 수 없습니다."));

        if (!course.getCreatedBy().equals(model.getUserId())) {
            throw new CourseAccessDeniedException("수업을 삭제할 권한이 없습니다.");
        }

        course.remove(LocalDateTime.now());
        courseWriteRepository.save(course);
    }
}
