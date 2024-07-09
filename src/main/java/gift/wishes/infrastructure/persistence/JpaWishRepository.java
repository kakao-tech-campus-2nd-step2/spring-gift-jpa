package gift.wishes.infrastructure.persistence;

import gift.core.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaWishRepository extends JpaRepository<WishEntity, Long> {

    boolean existsByUserIdAndProductId(Long userId, Long productId);

    @Query(
            value = "SELECT new gift.core.domain.product.Product(p.id, p.name, p.price, p.imageUrl) " +
                    "FROM ProductEntity p " +
                    "JOIN FETCH WishEntity w ON p.id = w.productId " +
                    "WHERE w.userId = :userId AND (p.name IS NOT NULL AND p.price IS NOT NULL AND p.imageUrl IS NOT NULL)"
    )
    List<Product> findAllByUserId(Long userId);

    void deleteByUserIdAndProductId(Long userId, Long productId);

}
