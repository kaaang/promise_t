package kr.co.promise_t.core.course;

import jakarta.persistence.*;
import java.util.UUID;
import kr.co.promise_t.core.course.vo.UserId;
import kr.co.promise_t.core.kernel.domain.BaseEntity;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "course_bcs_course_time_reservations")
public class CourseTimeReservation extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "course_times_id")
    private CourseTime courseTime;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    private UserId userId;
}
