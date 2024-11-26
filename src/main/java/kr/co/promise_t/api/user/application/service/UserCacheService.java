package kr.co.promise_t.api.user.application.service;

import java.time.Duration;
import java.util.Objects;
import kr.co.promise_t.api.kernel.infrastructure.KeyValueService;
import kr.co.promise_t.api.user.application.exception.RefreshTokenNotFoundException;
import kr.co.promise_t.core.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCacheService {
    private static final String REFRESH_TOKEN_FORMAT = "REFRESH_TOKEN:%s";

    private final KeyValueService keyValueService;

    public void storeRefreshToken(UserId id, String refreshToken, long duration) {
        keyValueService.set(
                String.format(REFRESH_TOKEN_FORMAT, id.getValue()),
                refreshToken,
                Duration.ofSeconds(duration));
    }

    public String getRefreshToken(UserId id) {
        var refreshToken =
                keyValueService.get(String.format(REFRESH_TOKEN_FORMAT, id.getValue()), String.class);
        if (Objects.isNull(refreshToken)) {
            throw new RefreshTokenNotFoundException("리프레쉬 토큰을 찾을 수 없습니다.");
        }

        return refreshToken;
    }

    public void removeRefreshToken(UserId id) {
        keyValueService.delete(String.format(REFRESH_TOKEN_FORMAT, id.getValue()));
    }
}
