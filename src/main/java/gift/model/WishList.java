package gift.model;

public class WishList {
    private Long id;
    private String userEmail;
    private Long productId;

    public WishList(String userEmail, Long productId) {
        this.userEmail = userEmail;
        this.productId = productId;
    }

    public WishList() {
    }

    public Long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
