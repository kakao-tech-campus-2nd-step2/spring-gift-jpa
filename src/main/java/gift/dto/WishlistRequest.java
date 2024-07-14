package gift.dto;

public class WishlistRequest {

    private final String email;
    private final long productId;

    public WishlistRequest(String email, long productId) {
        this.email = email;
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public long getProductId() {
        return productId;
    }
}
