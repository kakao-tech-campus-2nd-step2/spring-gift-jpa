package gift.member.persistence.repository;

import gift.member.persistence.entity.Wishlist;
import java.util.List;

public interface WishlistRepository {

    List<Wishlist> getWishListByMemberId(Long memberId);

    Long saveWishList(Wishlist wishList);
    
    Wishlist getWishListByMemberIdAndProductId(Long memberId, Long productId);

    Long updateWishlist(Wishlist wishList);

    void deleteWishlist(Long memberId, Long productId);
}
