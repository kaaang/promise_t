package kr.co.promise_t.core.course;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import kr.co.promise_t.core.kernel.domain.BaseEntity;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "course_bcs_course_time_reservation_attachments")
public class CourseTimeReservationAttachment extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "course_time_reservation_comment_id")
    private CourseTimeReservationComment comment;

    @NotNull private String path;

    public static CourseTimeReservationAttachment create(
            UUID id, String path, CourseTimeReservationComment comment) {
        return CourseTimeReservationAttachment.builder().id(id).comment(comment).path(path).build();
    }
}
