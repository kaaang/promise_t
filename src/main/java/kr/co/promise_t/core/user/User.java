package kr.co.promise_t.core.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import kr.co.promise_t.core.kernel.domain.BaseEntityAggregateRoot;
import kr.co.promise_t.core.user.vo.UserId;
import kr.co.promise_t.core.user.vo.UserRoleType;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "users")
@Where(clause = "deleted_at is null")
public class User extends BaseEntityAggregateRoot<User> implements UserDetails {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private UserId id;

    @Column @NotNull private String email;

    @Column @NotNull private String name;

    // TODO : 암호화 필요
    @Column @NotNull @ToString.Exclude private String password;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRoleType roleType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> roleType.name());
    }

    @Override
    public String getUsername() {
        return this.id.getValue().toString();
    }
}
