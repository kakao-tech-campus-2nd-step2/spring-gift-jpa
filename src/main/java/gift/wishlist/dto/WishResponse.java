package gift.wishlist.dto;

public class WishResponse {
    private Long id;
    private String productName;

    public WishResponse(Long id, String productName) {
        this.id = id;
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }
}
