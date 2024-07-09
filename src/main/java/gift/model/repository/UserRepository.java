package gift.model.repository;

import gift.model.User;
import java.util.Optional;

public interface UserRepository extends RepositoryInterface<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
}
