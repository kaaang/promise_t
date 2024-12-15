package kr.co.promise_t.api.course.application.command;

import static org.mockito.BDDMockito.*;

import java.util.UUID;
import kr.co.promise_t.api.config.UnitTestConfig;
import kr.co.promise_t.api.course.application.command.model.CreateCourseCommandModel;
import kr.co.promise_t.core.course.CourseRepository;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class CreateCourseCommandTest extends UnitTestConfig {
    @InjectMocks private CreateCourseCommand command;

    @Mock private CourseRepository courseRepository;
    @Mock private CreateCourseCommandModel model;

    @Test
    void shouldCallRepositorySave() {
        this.willReturnModel();

        command.execute(model);

        then(courseRepository).should(times(1)).save(any());
    }

    void willReturnModel() {
        given(model.getCourseId()).willReturn(CourseId.of(UUID.randomUUID()));
        given(model.getUserId()).willReturn(UserId.of(UUID.randomUUID()));
        given(model.getTitle()).willReturn("test");
        given(model.getDescription()).willReturn("test");
    }
}
