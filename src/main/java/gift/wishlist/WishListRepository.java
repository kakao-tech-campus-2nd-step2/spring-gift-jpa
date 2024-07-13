package gift.wishlist;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    Page<WishList> findAllByUserId(long id, Pageable pageable);

    List<WishList> findAllByUserId(long id);

    WishList findByUserIdAndProductId(long userId, long productId);

    Boolean existsByUserIdAndProductId(long userId, long productId);

    void deleteByUserIdAndProductId(long userId, long productId);
}
