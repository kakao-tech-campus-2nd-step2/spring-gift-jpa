package gift.DTO;

public class WishDTO {

    private Long id;
    private Long userId;
    private Long productId;
    private String productName;

    public WishDTO() {}

    public WishDTO(Long id, Long userId, Long productId, String productName) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
