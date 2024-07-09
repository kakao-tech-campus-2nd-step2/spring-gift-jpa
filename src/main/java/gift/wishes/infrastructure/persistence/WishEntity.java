package gift.wishes.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "wish")
public class WishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    public WishEntity() {
    }

    public WishEntity(Long id, Long userId, Long productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public WishEntity(Long userId, Long productId) {
        this.id = 0L;
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
}
