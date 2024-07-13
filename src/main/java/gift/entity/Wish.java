package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wish")
public class Wish {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "product_id", nullable = false, insertable = false, updatable = false)
    private Long productId;
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Wish() {
    }

    public Wish(Long id, Long productId, Long userId) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
    }

    public Wish(Long productId, Long userId) {
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


    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

}
