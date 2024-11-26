package kr.co.promise_t.api.user.application.command;

import java.util.UUID;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.api.user.application.command.model.CreateUserCommandModel;
import kr.co.promise_t.api.user.application.exception.UserAlreadyExistsException;
import kr.co.promise_t.core.user.UserData;
import kr.co.promise_t.core.user.UserFactory;
import kr.co.promise_t.core.user.UserId;
import kr.co.promise_t.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserCommand implements Command<CreateUserCommandModel> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void execute(CreateUserCommandModel model) {
        this.checkIfExists(model.getEmail());

        var user =
                new UserFactory(
                                UserData.builder()
                                        .id(UserId.of(UUID.randomUUID()))
                                        .email(model.getEmail())
                                        .name(model.getName())
                                        .password(passwordEncoder.encode(model.getPassword()))
                                        .roleType(model.getRoleType())
                                        .build())
                        .create();

        userRepository.save(user);
    }

    private void checkIfExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("이미 가입된 이메일 입니다.");
        }
    }
}
