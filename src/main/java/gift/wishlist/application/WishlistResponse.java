package gift.wishlist.application;

import gift.wishlist.domain.Wishlist;

public class WishlistResponse {
    private Long id;
    private String memberEmail;
    private Long productId;

    public WishlistResponse(Long id, String memberEmail, Long productId) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.productId = productId;
    }

    public static WishlistResponse from(Wishlist wishlist) {
        return new WishlistResponse(
                wishlist.getId(),
                wishlist.getMemberEmail(),
                wishlist.getProductId()
        );
    }

    public Long getId() {
        return id;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public Long getProductId() {
        return productId;
    }
}
