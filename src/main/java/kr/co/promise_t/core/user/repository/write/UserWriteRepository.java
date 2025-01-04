package kr.co.promise_t.core.user.repository.write;

import java.util.Optional;
import kr.co.promise_t.core.user.User;
import kr.co.promise_t.core.user.vo.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWriteRepository extends JpaRepository<User, UserId> {
    Optional<User> findByEmail(String email);
}
