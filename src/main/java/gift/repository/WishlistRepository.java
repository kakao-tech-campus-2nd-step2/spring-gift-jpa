package gift.repository;

import gift.DTO.Wishlist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByEmailAndProductId(String email, Long productId);

    List<Wishlist> findByEmail(String email);
}
