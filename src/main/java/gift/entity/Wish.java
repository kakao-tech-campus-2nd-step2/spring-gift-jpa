package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "wishes")
public class Wish {
    @Id
    private Long id;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false)
    private Long userId;

    public Wish() {
    }

    public Wish(Long id, Long productId, Long userId) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
    }
    public Wish( Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
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
}
