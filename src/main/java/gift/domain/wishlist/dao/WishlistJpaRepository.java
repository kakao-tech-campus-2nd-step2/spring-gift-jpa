package gift.domain.wishlist.dao;

import gift.domain.wishlist.entity.WishItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistJpaRepository extends JpaRepository<WishItem, Long> {
    List<WishItem> findAllByUser_id(Long userId);
}
