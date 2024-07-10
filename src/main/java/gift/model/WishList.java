package gift.model;

import java.util.Map;

public class WishList {

    private Long id;
    private Long memberId;
    private Map<Long, Integer> wishList;

    public WishList(Long id, Long memberId, Map<Long, Integer> wishList) {
        this.id = id;
        this.memberId = memberId;
        this.wishList = wishList;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Map<Long, Integer> getWishList() {
        return wishList;
    }

    public void updateProduct(Long productId, Integer count) {
        wishList.put(productId, count);
    }

    public void deleteProduct(Long productId) {
        wishList.remove(productId);
    }
}
