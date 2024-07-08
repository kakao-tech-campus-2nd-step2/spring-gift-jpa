package gift.repository.user;

import gift.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    void save(User user);
    void update(User user);
    void delete(Long id);
    Optional<User> findById(Long id);
    List<User> findAll();
    boolean existsByEmail(String email);
}
