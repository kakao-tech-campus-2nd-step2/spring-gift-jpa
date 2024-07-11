package gift.repository.wish;

import gift.model.wishlist.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findAllByUserId(Long userId);
    Optional<WishList> findByIdAndUserId(Long wishId, Long userId);
    Optional<WishList> findByUserIdAndAndProductId(Long userId, Long ProductId);


    void deleteByUserIdAndAndProductId(Long userId, Long productId);
}

