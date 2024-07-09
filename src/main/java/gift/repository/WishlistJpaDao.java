package gift.repository;

import gift.dto.Product;
import gift.dto.Wishlist;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishlistJpaDao extends JpaRepository<Wishlist, Long> {

    @Query(value = """
        SELECT w
        FROM Wishlist w
        WHERE w.email = :#{#wish.email} AND w.productId = :#{#wish.productId}
        """)
    Optional<Wishlist> findByWishlist(@Param("wish") Wishlist wish);

    @Query(value = """
        SELECT new Product(p.id, p.name, p.price, p.imageUrl)
        FROM Wishlist w, Product p
        WHERE w.productId = p.id
        and w.email = :email
        """)
    List<Product> findAllWishlistByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = """
        DELETE
        FROM Wishlist w
        WHERE w.email = :#{#wish.email} AND w.productId = :#{#wish.productId}
        """)
    void deleteByWishlist(@Param("wish") Wishlist wishlist);
}
