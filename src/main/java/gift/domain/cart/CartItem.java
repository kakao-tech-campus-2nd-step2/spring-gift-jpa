package gift.domain.cart;

public class CartItem {

    private Long id;

    private Long userId;

    private Long productId;

    public CartItem() {
    }

    public CartItem(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
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

    @Override
    public String toString() {
        return "CartItem{" +
               "id=" + id +
               ", userId=" + userId +
               ", productId=" + productId +
               '}';
    }
}
