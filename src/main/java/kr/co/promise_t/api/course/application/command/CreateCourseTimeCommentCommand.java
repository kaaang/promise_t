package kr.co.promise_t.api.course.application.command;

import jakarta.transaction.Transactional;
import java.util.UUID;
import kr.co.promise_t.api.course.application.command.model.CreateCourseTimeCommentCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseTimeNotFoundException;
import kr.co.promise_t.api.course.application.exception.CourseTimeReservationNotFoundException;
import kr.co.promise_t.api.course.application.query.CourseTimeQuery;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.CourseTimeReservationComment;
import kr.co.promise_t.core.course.repository.write.CourseTimeWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCourseTimeCommentCommand
        implements Command<CreateCourseTimeCommentCommandModel> {
    private final CourseTimeQuery courseTimeQuery;
    private final CourseTimeWriteRepository courseTimeWriteRepository;

    @Override
    @Transactional
    public void execute(CreateCourseTimeCommentCommandModel model) {
        var courseTime =
                courseTimeWriteRepository
                        .findWithReservationByIdAndReservationId(
                                model.getCourseTimeId(), model.getReservationId())
                        .orElseThrow(() -> new CourseTimeNotFoundException("수업 일정을 찾을 수 없습니다."));

        var reservation =
                courseTime.getReservations().stream()
                        .findFirst()
                        .orElseThrow(() -> new CourseTimeReservationNotFoundException("수업 예약 내역을 찾을 수 없습니다."));

        var comment =
                CourseTimeReservationComment.create(
                        UUID.randomUUID(), reservation, model.getUserid(), model.getContents());
        reservation.addComment(comment);

        courseTimeWriteRepository.save(courseTime);
    }
}
