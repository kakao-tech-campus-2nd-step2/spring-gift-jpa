package gift.domain.wish;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByEmail(String email);
    Optional<Wish> findByEmailAndProductId(String email, Long productId);
    void deleteByEmailAndProductId(String email, Long productId);
}
