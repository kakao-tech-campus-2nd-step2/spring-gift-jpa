package gift.wishlist.application;

import gift.wishlist.domain.Wishlist;

public class WishlistResponse {
    private Long id;
    private Long memberId;
    private Long productId;

    public WishlistResponse(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static WishlistResponse from(Wishlist wishlist) {
        return new WishlistResponse(
                wishlist.getId(),
                wishlist.getMember().getId(),
                wishlist.getProduct().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }
}
