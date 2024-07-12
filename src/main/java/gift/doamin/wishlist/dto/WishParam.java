package gift.doamin.wishlist.dto;

import gift.doamin.wishlist.entity.Wish;

public class WishParam {

    private Long productId;
    private Integer quantity;

    public WishParam(Wish wish) {
        this.productId = wish.getProduct().getId();
        this.quantity = wish.getQuantity();
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
