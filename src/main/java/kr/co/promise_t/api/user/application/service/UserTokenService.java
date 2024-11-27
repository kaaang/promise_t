package kr.co.promise_t.api.user.application.service;

import java.util.Objects;
import java.util.UUID;
import kr.co.promise_t.api.user.application.exception.RefreshTokenNotFoundException;
import kr.co.promise_t.api.user.application.exception.UserNotFoundException;
import kr.co.promise_t.api.user.application.listener.event.UserSignInEvent;
import kr.co.promise_t.api.user.application.service.payload.CreateTokenPayload;
import kr.co.promise_t.api.user.application.service.payload.RefreshTokenPayload;
import kr.co.promise_t.api.user.application.service.payload.UserTokenPayload;
import kr.co.promise_t.core.user.User;
import kr.co.promise_t.core.user.UserRepository;
import kr.co.promise_t.core.user.service.JwtTokenProvider;
import kr.co.promise_t.core.user.service.UserJwt;
import kr.co.promise_t.core.user.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ApplicationEventPublisher eventPublisher;
    private final UserCacheService userCacheService;

    public UserTokenPayload createToken(CreateTokenPayload payload) {
        var user = this.getUserOr404ByEmail(payload.getEmail());
        this.validatePassword(payload.getPassword(), user.getPassword());

        return this.createTokenBody(user);
    }

    private User getUserOr404ByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private void validatePassword(String raw, String to) {
        if (!passwordEncoder.matches(raw, to)) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    private UserTokenPayload createTokenBody(User user) {
        var jwt = jwtTokenProvider.generateToken(user.getId());
        this.publishSignInEvent(user.getId(), jwt);

        return UserTokenPayload.builder()
                .accessToken(jwt.getAccessToken())
                .refreshToken(jwt.getRefreshToken())
                .expiresIn(jwt.getExpiresIn())
                .refreshExpiresIn(jwt.getRefreshExpiresIn())
                .build();
    }

    public UserTokenPayload createTokenBodyBy(RefreshTokenPayload payload) {
        var userId = jwtTokenProvider.getUserId(payload.getRefreshToken());
        var user =
                userRepository
                        .findById(UserId.of(UUID.fromString(userId)))
                        .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        var savedToken = userCacheService.getRefreshToken(user.getId());
        if (!Objects.equals(savedToken, payload.getRefreshToken())) {
            throw new RefreshTokenNotFoundException("리프레쉬 토큰을 찾을 수 없습니다.");
        }

        return this.createTokenBody(user);
    }

    private void publishSignInEvent(UserId id, UserJwt jwt) {
        eventPublisher.publishEvent(
                new UserSignInEvent(this, id, jwt.getRefreshToken(), jwt.getRefreshExpiresIn()));
    }
}
