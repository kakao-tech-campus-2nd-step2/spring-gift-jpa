package gift.dto;

public class WishProduct {
    private String email;
    private Long productId;

    public WishProduct(String email, Long productId) {
        this.email = email;
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
