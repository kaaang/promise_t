package kr.co.promise_t.core.course.vo;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(
        of = {"value"},
        callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class CourseTimeId implements Serializable {
    private UUID value;

    public static CourseTimeId of(UUID value) {
        return new CourseTimeId(value);
    }
}
