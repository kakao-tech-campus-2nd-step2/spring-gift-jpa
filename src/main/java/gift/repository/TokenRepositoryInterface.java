package gift.repository;

import gift.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepositoryInterface extends JpaRepository<Token, Long> {
}
