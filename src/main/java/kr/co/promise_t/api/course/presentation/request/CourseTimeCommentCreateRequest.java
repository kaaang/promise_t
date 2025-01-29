package kr.co.promise_t.api.course.presentation.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseTimeCommentCreateRequest {
    @NotEmpty private String contents;
}
