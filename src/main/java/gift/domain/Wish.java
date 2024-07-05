package gift.domain;

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
}
