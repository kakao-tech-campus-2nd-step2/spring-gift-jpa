package gift.repository.user;

import gift.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findAllByEmail(String email);
}