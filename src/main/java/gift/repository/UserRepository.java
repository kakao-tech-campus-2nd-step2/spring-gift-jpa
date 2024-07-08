package gift.repository;

import gift.domain.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByPasswordAndEmail(String email,String password);
    Optional<User> findByEmail(String email);
}