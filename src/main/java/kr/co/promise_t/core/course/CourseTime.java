package kr.co.promise_t.core.course;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kr.co.promise_t.core.course.vo.CourseTimeData;
import kr.co.promise_t.core.kernel.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "course_bcs_course_times")
public class CourseTime extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "courses_id")
    private Course course;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int maxCapacity;

    @Builder.Default
    @OneToMany(mappedBy = "courseTime", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 100)
    private List<CourseTimeReservation> reservations = new ArrayList<>();

    public boolean canReserve() {
        return this.reservations.size() > this.maxCapacity;
    }

    public int getReservedCount() {
        return this.reservations.size();
    }

    public int getRemainingCapacity() {
        return this.maxCapacity - this.reservations.size();
    }

    public static CourseTime create(@Nonnull CourseTimeData data) {
        return CourseTime.builder()
                .id(data.getId())
                .course(data.getCourse())
                .startTime(data.getStartTime())
                .endTime(data.getEndTime())
                .maxCapacity(data.getMaxCapacity())
                .build();
    }
}
