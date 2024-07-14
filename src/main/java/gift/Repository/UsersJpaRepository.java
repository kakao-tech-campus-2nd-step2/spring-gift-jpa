package gift.Repository;

import gift.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersJpaRepository extends JpaRepository<Users, Long>{
    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailAndPassword(String email, String password);

}
