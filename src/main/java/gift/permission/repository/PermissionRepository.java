package gift.permission.repository;

import gift.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<User, Long> {
    // JPA가 메서드 이름으로 쿼리를 유추
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPassword(String password);
}
