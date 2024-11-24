package kr.co.promise_t.api.user.application.command;

import java.util.UUID;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.api.user.application.command.model.CreateUserCommandModel;
import kr.co.promise_t.core.user.UserData;
import kr.co.promise_t.core.user.UserFactory;
import kr.co.promise_t.core.user.UserId;
import kr.co.promise_t.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserCommand implements Command<CreateUserCommandModel> {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void execute(CreateUserCommandModel model) {
        var user =
                new UserFactory(
                                UserData.builder()
                                        .id(UserId.of(UUID.randomUUID()))
                                        .email(model.getEmail())
                                        .username(model.getUsername())
                                        .password(model.getPassword())
                                        .roleType(model.getRoleType())
                                        .build())
                        .create();

        userRepository.save(user);
    }
}
