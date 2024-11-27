package kr.co.promise_t.core.course;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "courses")
@Where(clause = "deleted_at is null")
public class Course extends BaseEntityAggregateRoot<Course> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private CourseId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "teacher_id", nullable = false))
    private UserId teacherId;

    @NotNull private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;
}
