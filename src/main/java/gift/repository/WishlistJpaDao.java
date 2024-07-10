package gift.repository;

import gift.dto.Wishlist;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishlistJpaDao extends JpaRepository<Wishlist, Long> {

    @Query(value = """
        SELECT w
        FROM Wishlist w
        WHERE w.member.email = :#{#wish.member.email} AND w.product.id = :#{#wish.product.id}
        """)
    Optional<Wishlist> findByWishlist(@Param("wish") Wishlist wish);

    @Transactional
    @Modifying
    @Query(value = """
        DELETE
        FROM Wishlist w
        WHERE w.member.email = :#{#wish.member.email} AND w.product.id = :#{#wish.product.id}
        """)
    void deleteByWishlist(@Param("wish") Wishlist wishlist);
}
