package gift.user.persistence;

import gift.common.persistence.RepositoryInterface;
import gift.user.domain.User;
import java.util.Optional;

public interface UserRepository extends RepositoryInterface<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
}
