package kr.co.promise_t.core.course;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kr.co.promise_t.core.course.vo.UserId;
import kr.co.promise_t.core.kernel.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;

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
    @JoinColumn(name = "course_time_id")
    private CourseTime courseTime;

    @Builder.Default
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 100)
    private List<CourseTimeReservationComment> comments = new ArrayList<>();

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    private UserId userId;

    public static CourseTimeReservation create(UUID id, CourseTime courseTime, UserId userId) {
        return CourseTimeReservation.builder().id(id).courseTime(courseTime).userId(userId).build();
    }

    public void addComment(@Nonnull CourseTimeReservationComment comment) {
        this.comments.add(comment);
    }
}
