package gift.domain.user.repository;

import gift.domain.user.User;
import gift.domain.user.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
