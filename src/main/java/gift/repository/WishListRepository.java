package gift.repository;

import gift.model.wishList.WishItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishItem,Long> {
    List<WishItem> findAllByUserId(Long userId);
    void deleteByUserIdAndItemId(Long userId,Long itemId);
}
