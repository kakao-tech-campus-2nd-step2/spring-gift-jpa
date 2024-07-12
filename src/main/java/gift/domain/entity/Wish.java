package gift.domain.entity;

import gift.domain.dto.request.WishRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long userId;
    private Long quantity;

    public Wish(Long productId, Long userId, Long quantity) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
    }

    protected Wish() {
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void set(WishRequest request) {
        this.quantity = request.quantity();
    }
}
