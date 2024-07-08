package gift.wishlist.domain;

public class Wishlist {
    private Long id;
    private String memberEmail;
    private Long productId;

    public Wishlist(String memberEmail, Long productId) {
        this(null, memberEmail, productId);
    }

    public Wishlist(Long id, String memberEmail, Long productId) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.productId = productId;
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
