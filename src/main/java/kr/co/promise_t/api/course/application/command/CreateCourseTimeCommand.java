package kr.co.promise_t.api.course.application.command;

import jakarta.transaction.Transactional;
import kr.co.promise_t.api.course.application.command.model.CreateCourseTimeCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseNotFoundException;
import kr.co.promise_t.api.course.application.exception.DuplicatedCourseTimeException;
import kr.co.promise_t.api.course.application.query.CourseTimeQuery;
import kr.co.promise_t.api.course.application.service.CourseTimeService;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.core.course.*;
import kr.co.promise_t.core.course.repository.read.CourseReadRepository;
import kr.co.promise_t.core.course.repository.write.CourseTimeWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCourseTimeCommand implements Command<CreateCourseTimeCommandModel> {
    private final CourseReadRepository courseReadRepository;
    private final CourseTimeWriteRepository courseTimeWriteRepository;
    private final CourseTimeQuery courseTimeQuery;
    private final CourseTimeService courseTimeService;

    @Override
    @Transactional
    public void execute(CreateCourseTimeCommandModel model) {
        if (!courseReadRepository.existsByIdAndCreatedBy(model.getCourseId(), model.getUserId())) {
            throw new CourseNotFoundException("수업을 찾을 수 없습니다.");
        }

        if (!courseTimeQuery.canCreateCourseTime(
                model.getCourseId(), model.getStartTime(), model.getEndTime())) {
            throw new DuplicatedCourseTimeException("중복된 수업 시간이 존재합니다.");
        }

        var courseTime =
                new CourseTimeFactory(
                                CourseTimeData.builder()
                                        .id(model.getId())
                                        .courseId(model.getCourseId())
                                        .startTime(model.getStartTime())
                                        .endTime(model.getEndTime())
                                        .maxCapacity(model.getMaxCapacity())
                                        .userId(model.getUserId())
                                        .build())
                        .create();

        courseTimeWriteRepository.save(courseTime);
        courseTimeService.storeCourseTimeReservedCount(model.getId(), model.getEndTime());
    }
}
