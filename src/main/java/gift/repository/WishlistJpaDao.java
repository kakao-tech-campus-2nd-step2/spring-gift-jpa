package gift.repository;

import gift.dto.Product;
import gift.dto.Wishlist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishlistJpaDao extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByEmailAndProductId(String email, Long productId);

    @Query(value = """
        SELECT new Product(p.id, p.name, p.price, p.imageUrl)
        FROM Wishlist w, Product p
        WHERE w.productId = p.id
        and w.email = :email
        """)
    List<Product> findAllWishlistByEmail(@Param("email") String email);

    void deleteByEmailAndProductId(String email, Long productId);
}
