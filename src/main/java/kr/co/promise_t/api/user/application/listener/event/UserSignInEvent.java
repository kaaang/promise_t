package kr.co.promise_t.api.user.application.listener.event;

import kr.co.promise_t.core.user.UserId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserSignInEvent extends ApplicationEvent {
    private final UserId userId;
    private final String refreshToken;
    private final long duration;

    public UserSignInEvent(Object source, UserId userId, String refreshToken, long duration) {
        super(source);
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.duration = duration;
    }
}
