package gift.repository;

import gift.entity.ProductWishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductWishlistRepository extends JpaRepository<ProductWishlist, Long> {
    Page<ProductWishlist> findByWishlistId(Long id, Pageable pageable);

    List<ProductWishlist> findByProductId(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductWishlist pw WHERE pw.product.id = :productId AND pw.wishlist.id = :wishlistId")
    void deleteByProductIdAndWishlistId(@Param("productId") Long productId, @Param("wishlistId") Long wishlistId);
}
