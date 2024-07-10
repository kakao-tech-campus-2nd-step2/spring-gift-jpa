package gift.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserAccountEntity> findByEmail(String email);

}
