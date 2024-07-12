package gift.model.wishlist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 상품 데이터에 대한 데이터베이스 처리를 담당하는 인터페이스
 */
@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, Long> {

    List<WishListEntity> findAllByUserId(Long userId);

    int deleteWishListsByUserId(Long userId);

    int deleteWishListByUserIdAndProductId(Long userId, Long productId);
}