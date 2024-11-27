package kr.co.promise_t.api.course.presentation.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateRequest {
    @NotEmpty private String title;
    private String description;
}
