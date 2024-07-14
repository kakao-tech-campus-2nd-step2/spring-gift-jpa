package gift.api.wishlist;

import java.io.Serializable;
import java.util.Objects;

public class WishId implements Serializable {

    private Long member;
    private Long product;

    protected WishId(){
    }

    public WishId(Long member, Long product) {
        this.member = member;
        this.product = product;
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
        return Objects.equals(member, wishId.member) &&
            Objects.equals(product, wishId.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, product);
    }
}
