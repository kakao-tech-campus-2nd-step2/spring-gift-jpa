package gift.permission.repository;

import gift.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<User, Long> {
    // JPA가 메서드 이름으로 쿼리를 유추
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
