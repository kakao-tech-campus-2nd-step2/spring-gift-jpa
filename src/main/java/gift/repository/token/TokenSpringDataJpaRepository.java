package gift.repository.token;

import gift.domain.TokenAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenSpringDataJpaRepository extends JpaRepository<TokenAuth, String> {
    Optional<TokenAuth> findByToken(String token);
}