package gift.vo;

public class WishProduct {

    private Long wishProductId;
    private String memberEmail; // 회원(Member)의 pk(email)을 참조하는 외래 키(fk)
    private Long productId;

    public WishProduct(String memberEmail, Long productId) {
        this(null, memberEmail, productId);
    }

    public WishProduct(Long wishProductId, String memberEmail, Long productId) {
        this.wishProductId = wishProductId;
        this.memberEmail = memberEmail;
        this.productId = productId;
    }

    public Long getWishProductId() {
        return wishProductId;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public Long getProductId() {
        return productId;
    }
}
