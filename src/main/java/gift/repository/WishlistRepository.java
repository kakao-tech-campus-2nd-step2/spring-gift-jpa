package gift.repository;

import gift.domain.Wishlist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByUser_EmailAndProduct_Id(String email, Long productId);

    List<Wishlist> findByUser_Email(String email);
}
