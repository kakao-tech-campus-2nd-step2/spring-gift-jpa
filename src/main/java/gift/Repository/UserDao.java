package gift.Repository;

import gift.DTO.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);
}
