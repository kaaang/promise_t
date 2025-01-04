package kr.co.promise_t.api.course.application.command;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;
import kr.co.promise_t.api.config.UnitTestConfig;
import kr.co.promise_t.api.course.application.command.model.DeleteCourseCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseAccessDeniedException;
import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.core.course.Course;
import kr.co.promise_t.core.course.repository.write.CourseWriteRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class DeleteCourseCommandTest extends UnitTestConfig {
    @InjectMocks private DeleteCourseCommand command;

    @Mock private CourseWriteRepository courseWriteRepository;
    @Mock private DeleteCourseCommandModel model;

    @Test
    void shouldThrowCourseNotFoundException_WhenCourseNotExists() {
        given(courseWriteRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> command.execute(model)).isInstanceOf(CourseNotFoundException.class);
    }

    @Test
    void shouldThrowCourseAccessDeniedException_WhenNotEqualsTeacherId() {
        var requestTeacherId = UserId.of(UUID.randomUUID());
        var otherTeacherId = UserId.of(UUID.randomUUID());
        var course = mock(Course.class);

        given(model.getUserId()).willReturn(requestTeacherId);
        given(model.getCourseId()).willReturn(CourseId.of(UUID.randomUUID()));
        given(courseWriteRepository.findById(any())).willReturn(Optional.of(course));
        given(course.getCreatedBy()).willReturn(otherTeacherId);

        assertThatThrownBy(() -> command.execute(model))
                .isInstanceOf(CourseAccessDeniedException.class);
    }

    @Test
    void shouldCallRemoveAndSave() {
        var teacherId = UserId.of(UUID.randomUUID());
        var course = mock(Course.class);

        given(model.getUserId()).willReturn(teacherId);
        given(model.getCourseId()).willReturn(CourseId.of(UUID.randomUUID()));
        given(courseWriteRepository.findById(any())).willReturn(Optional.of(course));
        given(course.getCreatedBy()).willReturn(teacherId);

        command.execute(model);

        then(course).should(times(1)).remove(any());
        then(courseWriteRepository).should(times(1)).save(any());
    }
}
