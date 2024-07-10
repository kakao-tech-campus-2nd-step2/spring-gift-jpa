package gift.domain.cart;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
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
