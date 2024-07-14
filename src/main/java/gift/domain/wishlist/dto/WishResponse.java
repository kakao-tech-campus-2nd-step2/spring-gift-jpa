package gift.domain.wishlist.dto;

public class WishResponse {

    private Long id;
    private Long memberId;
    private Long productId;

    public WishResponse() {
    }

    public WishResponse(Long memberId, Long productId) {
        this(null, memberId, productId);
    }

    public WishResponse(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getId() {
        return this.id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

}
