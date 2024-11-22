package kr.co.promise_t.core.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.UUID;
import lombok.*;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "value")
public class UserId implements Serializable {
    @NonNull
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id")
    private UUID value;

    public static UserId of(UUID value) {
        return new UserId(value);
    }
}
