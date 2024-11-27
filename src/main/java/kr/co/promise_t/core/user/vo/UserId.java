package kr.co.promise_t.core.user.vo;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.*;

@EqualsAndHashCode(
        of = {"value"},
        callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class UserId implements Serializable {
    private UUID value;

    public static UserId of(UUID value) {
        return new UserId(value);
    }
}
