package gift.wishlist.model;

import gift.wishlist.model.dto.Wish;
import gift.wishlist.model.dto.WishListResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<Wish, Long> {
    @Query(value = """
            SELECT p.id, p.name, p.price, p.image_url, w.quantity 
            FROM Wish w
            JOIN Product p ON w.product_id = p.id
            WHERE w.user_id = ?
            """, nativeQuery = true)
    List<WishListResponse> findWishesByUserId(@Param("userId") Long userId);

    Optional<Wish> findByIdAndUserIdAndIsActiveTrue(Long id, Long userId);
}
