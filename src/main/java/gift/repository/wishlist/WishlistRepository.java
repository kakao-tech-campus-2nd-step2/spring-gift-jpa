package gift.repository.wishlist;

import gift.domain.WishlistItem;

import java.util.List;

public interface WishlistRepository {
    void addItem(WishlistItem item);
    void deleteItem(Long productId);
    List<WishlistItem> getItemsByMemberId(Long memberId);
}
