package gift.repository;

import gift.domain.User;
import gift.dto.UserLogin;
import gift.dto.UserLogin.Request;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByAccessToken(String accessToken);
}
