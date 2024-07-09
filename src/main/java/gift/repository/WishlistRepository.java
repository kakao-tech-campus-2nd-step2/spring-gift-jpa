package gift.repository;

import gift.domain.WishlistItem;

import java.util.List;

public interface WishlistRepository {
    void addItem(WishlistItem item);
    void deleteItem(Long itemId);
    List<WishlistItem> getItemsByMemberId(Long memberId);
}
