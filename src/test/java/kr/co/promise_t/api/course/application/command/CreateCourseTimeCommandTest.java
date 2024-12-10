package kr.co.promise_t.api.course.application.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import kr.co.promise_t.api.config.UnitTestConfig;
import kr.co.promise_t.api.course.application.command.model.CreateCourseTimeCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.api.course.application.exception.DuplicatedCourseTimeException;
import kr.co.promise_t.api.course.application.query.CourseTimeQuery;
import kr.co.promise_t.core.course.Course;
import kr.co.promise_t.core.course.CourseRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class CreateCourseTimeCommandTest extends UnitTestConfig {
    @InjectMocks private CreateCourseTimeCommand command;

    @Mock private CreateCourseTimeCommandModel model;
    @Mock private CourseRepository courseRepository;
    @Mock private CourseTimeQuery courseTimeQuery;

    @Test
    void shouldBeThrowCourseNotFoundException_WhenCourseIsNotExits() {
        given(model.getCourseId()).willReturn(CourseId.of(UUID.randomUUID()));
        given(model.getTeacherId()).willReturn(UserId.of(UUID.randomUUID()));
        given(courseRepository.findByIdAndUserId(any(), any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> command.execute(model)).isInstanceOf(CourseNotFoundException.class);
    }

    @Test
    void shouldThrowDuplicatedCourseTimeException() {
        var course = mock(Course.class);
        given(model.getCourseId()).willReturn(CourseId.of(UUID.randomUUID()));
        given(model.getTeacherId()).willReturn(UserId.of(UUID.randomUUID()));
        given(courseRepository.findByIdAndUserId(any(), any())).willReturn(Optional.of(course));
        given(courseTimeQuery.canCreateCourseTime(any(), any(), any())).willReturn(false);

        assertThatThrownBy(() -> command.execute(model))
                .isInstanceOf(DuplicatedCourseTimeException.class);
    }

    @Test
    void shouldCallRepositorySave() {
        var course = mock(Course.class);
        given(courseTimeQuery.canCreateCourseTime(any(), any(), any())).willReturn(true);
        given(courseRepository.findByIdAndUserId(any(), any())).willReturn(Optional.of(course));
        this.willReturnModel();

        command.execute(model);

        then(courseRepository).should(times(1)).save(any());
    }

    void willReturnModel() {
        given(model.getCourseId()).willReturn(CourseId.of(UUID.randomUUID()));
        given(model.getTeacherId()).willReturn(UserId.of(UUID.randomUUID()));
        given(model.getMaxCapacity()).willReturn(10);
        given(model.getStartTime()).willReturn(LocalDateTime.now().minusHours(1));
        given(model.getEndTime()).willReturn(LocalDateTime.now().plusHours(1));
        given(model.getId()).willReturn(UUID.randomUUID());
    }
}
