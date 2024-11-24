package kr.co.promise_t.api.user.presentation;

import jakarta.validation.Valid;
import kr.co.promise_t.api.kernel.command.CommandExecutor;
import kr.co.promise_t.api.user.application.command.CreateUserCommand;
import kr.co.promise_t.api.user.application.command.model.CreateUserCommandModel;
import kr.co.promise_t.api.user.presentation.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserCommandController {
    private final CreateUserCommand createUserCommand;

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest request) {
        new CommandExecutor<>(
                        createUserCommand,
                        CreateUserCommandModel.builder()
                                .email(request.getEmail())
                                .username(request.getUsername())
                                .password(request.getPassword())
                                .roleType(request.getRoleType())
                                .build())
                .invoke();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
