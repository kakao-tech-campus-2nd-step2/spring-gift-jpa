package gift.repository;

import gift.model.User;
import gift.model.UserDTO;

import java.util.Optional;

public interface UserRepository {
    User save(UserDTO user);

    Optional<User> findByEmail(String email);
}
