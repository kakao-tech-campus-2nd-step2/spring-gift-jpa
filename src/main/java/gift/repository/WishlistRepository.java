package gift.repository;

import gift.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUserEmail(String email);
    void deleteByUserEmailAndProductId(String email, Long productId);
}