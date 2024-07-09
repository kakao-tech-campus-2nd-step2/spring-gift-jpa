package gift.api.wishlist;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.validation.constraints.NotNull;

@Entity
@IdClass(WishListId.class)
public class WishList {
    @Id
    private Long memberId;
    @Id
    private Long productId;
    @NotNull
    private Integer quantity;

    protected WishList() {
    }

    public WishList(Long memberId, WishListRequest wishListRequest) {
        this.memberId = memberId;
        this.productId = wishListRequest.productId();
        this.quantity = wishListRequest.quantity();
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
