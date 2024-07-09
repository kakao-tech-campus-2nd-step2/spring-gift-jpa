package gift.model;

public class Wishlist {

    private Long id;

    private String userEmail;

    private Long productId;

    public Wishlist(Long id, String userEmail, Long productId) {
        this.id = id;
        this.userEmail = userEmail;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Wishlist{" +
            "id=" + id +
            ", userEmail='" + userEmail + '\'' +
            ", productId=" + productId +
            '}';
    }
}
