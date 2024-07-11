package gift.repository;

import gift.model.WishListItem;

import java.util.List;
import java.util.Optional;

public interface WishListRepository {
    void addWishListItem(WishListItem item);
    void removeWishListItem(Long id);
    List<WishListItem> findWishListByMemberId(Long memberId);
    Optional<WishListItem> findById(Long id);
}
