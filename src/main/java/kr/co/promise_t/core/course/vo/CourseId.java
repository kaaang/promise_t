package kr.co.promise_t.core.course.vo;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.*;

@EqualsAndHashCode(
        of = {"value"},
        callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class CourseId implements Serializable {
    private UUID value;

    public static CourseId of(UUID value) {
        return new CourseId(value);
    }
}
