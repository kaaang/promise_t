package kr.co.promise_t.api.user.application.listener;

import kr.co.promise_t.api.user.application.listener.event.UserSignInEvent;
import kr.co.promise_t.api.user.application.service.UserCacheService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSignInEventListener implements ApplicationListener<UserSignInEvent> {
    private final UserCacheService userCacheService;

    @Override
    public void onApplicationEvent(@NonNull UserSignInEvent event) {
        userCacheService.storeRefreshToken(
                event.getUserId(), event.getRefreshToken(), event.getDuration());
    }
}
