package gift.DTO;

public class Wishlist {

    private String email;
    private Long productId;

    public Wishlist(String email, Long productId) {
        this.email = email;
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public Long getProductId() {
        return productId;
    }
}
