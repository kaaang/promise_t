package kr.co.promise_t.api.course.application.command;

import kr.co.promise_t.api.course.application.command.model.CreateReservationCommandModel;
import kr.co.promise_t.api.course.application.exception.AlreadyCourseTimeReservationException;
import kr.co.promise_t.api.course.application.exception.CourseTimeNotFoundException;
import kr.co.promise_t.api.course.application.exception.PendingCourseTimeReservationException;
import kr.co.promise_t.api.course.application.exception.ReservationNotAllowedException;
import kr.co.promise_t.api.course.application.listener.event.CourseTimeReservedEvent;
import kr.co.promise_t.api.course.application.service.CourseTimeCacheService;
import kr.co.promise_t.api.course.application.service.CourseTimeMessageService;
import kr.co.promise_t.api.course.application.service.vo.ReservationStatus;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.CourseTime;
import kr.co.promise_t.core.course.repository.read.CourseTimeReservationReadRepository;
import kr.co.promise_t.core.course.repository.write.CourseTimeWriteRepository;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateReservationCommand implements Command<CreateReservationCommandModel> {
    private final CourseTimeWriteRepository courseTimeWriteRepository;
    private final CourseTimeMessageService courseTimeMessageService;
    private final CourseTimeCacheService courseTimeCacheService;
    private final CourseTimeReservationReadRepository courseTimeReservationReadRepository;

    @Override
    @Transactional
    public void execute(CreateReservationCommandModel model) {
        var time =
                courseTimeWriteRepository
                        .findById(model.getId())
                        .orElseThrow(() -> new CourseTimeNotFoundException("수업 일정을 찾을 수 없습니다."));

        if (time.canReserve()) {
            throw new ReservationNotAllowedException("예약을 할 수 없는 상태입니다.");
        }

        this.checkReservationsStatus(time, model.getUserId());
        courseTimeCacheService.storeCourseTimeReservationStatus(
                time.getId(), model.getUserId(), ReservationStatus.PENDING);
        courseTimeMessageService.sendCourseTimeReservedMessage(
                CourseTimeReservedEvent.builder()
                        .courseTimeId(time.getId())
                        .userId(model.getUserId())
                        .build());
    }

    private void checkReservationsStatus(CourseTime time, UserId userId) {
        courseTimeCacheService
                .getCourserTimeReservationStatus(time.getId(), userId)
                .ifPresent(
                        status -> {
                            if (status.equals(ReservationStatus.PENDING)) {
                                throw new PendingCourseTimeReservationException("예약 대기중인 수업입니다.");
                            }

                            if (status.equals(ReservationStatus.SUCCESS)) {
                                throw new AlreadyCourseTimeReservationException("이미 예약된 수업입니다.");
                            }
                        });

        courseTimeReservationReadRepository
                .findByCourseTimeAndUserId(time, userId)
                .ifPresent(
                        reservation -> {
                            throw new AlreadyCourseTimeReservationException("이미 예약된 수업입니다.");
                        });
    }
}
