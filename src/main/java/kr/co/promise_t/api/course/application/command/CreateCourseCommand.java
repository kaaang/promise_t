package kr.co.promise_t.api.course.application.command;

import kr.co.promise_t.api.course.application.command.model.CreateCourseCommandModel;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.CourseData;
import kr.co.promise_t.core.course.CourseFactory;
import kr.co.promise_t.core.course.repository.write.CourseWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCourseCommand implements Command<CreateCourseCommandModel> {
    private final CourseWriteRepository courseWriteRepository;

    @Override
    public void execute(CreateCourseCommandModel model) {
        var course =
                new CourseFactory(
                                CourseData.builder()
                                        .courseId(model.getCourseId())
                                        .createdBy(model.getUserId())
                                        .title(model.getTitle())
                                        .description(model.getDescription())
                                        .build())
                        .create();
        courseWriteRepository.save(course);
    }
}
