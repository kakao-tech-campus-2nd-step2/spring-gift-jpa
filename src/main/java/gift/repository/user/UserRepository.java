package gift.repository.user;

import gift.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    public User findAllByEmail(String email);
}