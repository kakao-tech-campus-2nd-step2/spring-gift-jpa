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
    @Query("SELECT new gift.wishlist.model.dto.WishListResponse(w.id, p.id, p.name, p.price, p.imageUrl, w.quantity) " +
            "FROM Wish w JOIN Product p ON w.productId = p.id " +
            "WHERE w.userId = :userId AND w.isActive = true")
    List<WishListResponse> findWishesByUserId(@Param("userId") Long userId);

    Optional<Wish> findByIdAndIsActiveTrue(Long id);

    Optional<Wish> findByIdAndUserIdAndIsActiveTrue(Long id, Long userId);
}
