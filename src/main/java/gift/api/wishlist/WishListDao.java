package gift.api.wishlist;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class WishListDao {

    private final WishListRepository wishListRepository;

    public WishListDao(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public List<WishList> getAllWishes(Long memberId) {
        return wishListRepository.findByMemberId(memberId);
    }

    public void insert(WishListRequest wishListRequest, Long memberId) {
        wishListRepository.save(new WishList(memberId, wishListRequest));
    }

    public void update(WishListRequest wishListRequest, Long memberId) {
        wishListRepository.save(new WishList(memberId, wishListRequest));
    }

    public void delete(WishListRequest wishListRequest, Long memberId) {
        wishListRepository.deleteByMemberIdAndProductId(memberId, wishListRequest.productId());
    }
}
