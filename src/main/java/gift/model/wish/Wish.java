package gift.model.wish;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int count;

    protected Wish() {
    }

    public Wish(Long id, Long userId, Long productId, int count) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.count = count;
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

    public int getCount() {
        return count;
    }

    public void updateWish(int count) {
        this.count = count;
    }
}
