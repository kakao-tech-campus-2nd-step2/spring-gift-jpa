package gift.repository;

import gift.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Boolean save(User user);
    Boolean isExistEmail(String email);
    List<User> findAll();
    Optional<User> isExistUser(User user);
}
