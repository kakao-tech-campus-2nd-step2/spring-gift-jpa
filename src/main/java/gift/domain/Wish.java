package gift.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Wish {

    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt == null ? null : createdAt;
    }
}
