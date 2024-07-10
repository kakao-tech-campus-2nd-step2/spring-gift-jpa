package gift.repository;

import gift.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByMemberEmail(String email);

    void deleteByMemberEmailAndProductId(String email, Long productId);
}