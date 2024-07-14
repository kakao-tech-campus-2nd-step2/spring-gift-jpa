package gift.domain.wish;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "wishes")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int amount;

    @Column(name = "is_deleted", nullable = false) // 이 부분을 추가하여 매핑 수정
    private boolean isDeleted = false;

    public Wish() {}

    public Wish(Long productId, Long userId, int amount) {
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
        this.isDeleted = false;
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

    public int getAmount() {
        return amount;
    }

    public boolean getIsDeleted() {
        return isDeleted;
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
