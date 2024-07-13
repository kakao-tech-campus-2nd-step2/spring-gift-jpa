package gift.wishlist.repository;

import gift.wishlist.entity.WishList;
import gift.wishlist.model.WishListId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, WishListId> {
    // 페이지로 가져오는 함수
    Page<WishList> findByWishListIdUserUserId(Long userId, Pageable pageable);

    Page<WishList> findByPrice(int price);

    // 다 가져오는 함수
    List<WishList> findByWishListIdUserUserId(Long userId);
}
