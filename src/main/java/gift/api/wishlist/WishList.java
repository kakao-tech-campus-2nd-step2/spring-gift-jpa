package gift.api.wishlist;

public class WishList {

    private Long memberId;
    private Long productId;
    private Integer quantity;

    public WishList(Long memberId, Long productId, Integer quantity) {
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
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
