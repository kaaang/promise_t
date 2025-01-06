package kr.co.promise_t.api.course.presentation.response;

import kr.co.promise_t.api.course.application.service.vo.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseTimeReservationStatusResponse {
    private ReservationStatus status;
}
