package gift.repository;

import gift.entity.Product;
import gift.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByEmail(String email);

    @Query("SELECT p FROM Product p WHERE p.wishlist.email = :email")
    Page<Product> findWishlistProductByEmail(@Param("email") String email, Pageable pageable);
}
