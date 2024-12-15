package kr.co.promise_t.core.course;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import kr.co.promise_t.core.course.vo.CourseId;
import kr.co.promise_t.core.course.vo.UserId;
import kr.co.promise_t.core.kernel.domain.BaseEntityAggregateRoot;
import lombok.*;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "course_bcs_courses")
@Where(clause = "deleted_at is null")
public class Course extends BaseEntityAggregateRoot<Course> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private CourseId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "created_by", nullable = false))
    private UserId createdBy;

    @NotNull private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;

    public void remove(@Nonnull LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
