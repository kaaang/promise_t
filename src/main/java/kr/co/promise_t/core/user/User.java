package kr.co.promise_t.core.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.promise_t.core.kernel.domain.BaseEntityAggregateRoot;
import kr.co.promise_t.core.user.vo.UserRoleType;
import lombok.*;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "users")
@Where(clause = "deleted_at is null")
public class User extends BaseEntityAggregateRoot<User> {
    // TODO : UserDetails 추가해야함

    @EmbeddedId private UserId id;

    @Column @NotNull private String email;

    @Column @NotNull private String username;

    // TODO : 암호화 필요
    @Column @NotNull private String password;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRoleType roleType;
}
