package kr.co.promise_t.core.user;

import java.util.Optional;
import kr.co.promise_t.core.user.vo.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
    Optional<User> findByEmail(String email);
}
