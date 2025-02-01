package kr.co.promise_t.api.course.application.command.model;

import jakarta.annotation.Nonnull;
import java.util.UUID;
import kr.co.promise_t.api.kernel.command.CommandModel;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class CreateCourseTimeCommentCommandModel implements CommandModel {
    @Nonnull private CourseTimeId courseTimeId;
    @Nonnull private UUID reservationId;
    @Nonnull private UserId userid;
    @Nonnull private String contents;
    private MultipartFile file;
}
