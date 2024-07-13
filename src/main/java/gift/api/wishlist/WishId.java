package gift.api.wishlist;

import java.io.Serializable;
import java.util.Objects;

public class WishId implements Serializable {

    private Long memberId;
    private Long productId;

    protected WishId(){
    }

    public WishId(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WishId wishId = (WishId) o;
        return Objects.equals(memberId, wishId.memberId) &&
            Objects.equals(productId, wishId.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, productId);
    }
}
