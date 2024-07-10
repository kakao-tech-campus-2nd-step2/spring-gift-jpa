package gift.api.wishlist;

import java.io.Serializable;

public class WishId implements Serializable {

    private Long memberId;
    private Long productId;

    protected WishId(){
    }

    public WishId(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }
}
