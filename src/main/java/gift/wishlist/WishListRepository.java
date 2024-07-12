package gift.wishlist;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findAllByUserId(long id, Sort sort);

    List<WishList> findAllByUserId(long id);

    WishList findByUserIdAndProductId(long userId, long productId);

    Boolean existsByUserIdAndProductId(long userId, long productId);

    void deleteByUserIdAndProductId(long userId, long productId);
}
