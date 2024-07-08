package gift.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByEmailAndPassword(String email, String password);
}
