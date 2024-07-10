package gift.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    Boolean existsByEmailAndPassword(String email, String password);
}
