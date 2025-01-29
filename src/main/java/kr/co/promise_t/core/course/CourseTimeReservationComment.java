package kr.co.promise_t.core.course;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import kr.co.promise_t.core.course.vo.UserId;
import kr.co.promise_t.core.kernel.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "course_bcs_course_time_reservation_comments")
@DynamicUpdate
public class CourseTimeReservationComment extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "course_time_reservation_id")
    private CourseTimeReservation reservation;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "created_by", nullable = false))
    private UserId createdBy;

    public static CourseTimeReservationComment create(
            UUID id, CourseTimeReservation courseTimeReservation, UserId createdBy, String contents) {
        return CourseTimeReservationComment.builder()
                .id(id)
                .reservation(courseTimeReservation)
                .createdBy(createdBy)
                .contents(contents)
                .build();
    }
}
