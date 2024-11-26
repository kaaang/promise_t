package kr.co.promise_t.api.user.presentation;

import jakarta.validation.Valid;
import kr.co.promise_t.api.user.application.service.UserTokenService;
import kr.co.promise_t.api.user.application.service.payload.CreateTokenPayload;
import kr.co.promise_t.api.user.application.service.payload.RefreshTokenPayload;
import kr.co.promise_t.api.user.presentation.request.UserRefreshRequest;
import kr.co.promise_t.api.user.presentation.request.UserSignInRequest;
import kr.co.promise_t.api.user.presentation.response.UserTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class SignInCommandController {
    private final UserTokenService userTokenService;

    @PostMapping(value = "/signin")
    public ResponseEntity<Object> signIn(@Valid @RequestBody UserSignInRequest request) {
        var token =
                userTokenService.createToken(
                        CreateTokenPayload.builder()
                                .email(request.getEmail())
                                .password(request.getPassword())
                                .build());

        return ResponseEntity.ok(
                UserTokenResponse.builder()
                        .accessToken(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                        .expiresIn(token.getExpiresIn())
                        .refreshExpiresIn(token.getRefreshExpiresIn())
                        .build());
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<Object> refresh(@Valid @RequestBody UserRefreshRequest request) {
        var token =
                userTokenService.createTokenBodyBy(
                        RefreshTokenPayload.builder().refreshToken(request.getRefreshToken()).build());

        return ResponseEntity.ok(
                UserTokenResponse.builder()
                        .accessToken(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                        .expiresIn(token.getExpiresIn())
                        .refreshExpiresIn(token.getRefreshExpiresIn())
                        .build());
    }
}
