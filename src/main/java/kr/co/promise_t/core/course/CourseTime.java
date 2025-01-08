package kr.co.promise_t.core.course;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.CourseTimeId;
import kr.co.promise_t.core.course.vo.UserId;
import kr.co.promise_t.core.kernel.domain.BaseEntityAggregateRoot;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "course_bcs_course_times")
@DynamicUpdate
@Where(clause = "deleted_at is null")
public class CourseTime extends BaseEntityAggregateRoot<CourseTime> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private CourseTimeId id;

    @Nonnull
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_id"))
    private CourseId courseId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int maxCapacity;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "created_by", nullable = false))
    private UserId createdBy;

    @Builder.Default
    @OneToMany(mappedBy = "courseTime", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 100)
    private List<CourseTimeReservation> reservations = new ArrayList<>();

    public void remove(@Nonnull LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean canReserve() {
        return this.getStartTime().isBefore(LocalDateTime.now());
    }

    public void addReservation(@Nonnull CourseTimeReservation reservation) {
        this.reservations.add(reservation);
    }
}
