package gift.doamin.wishlist.entity;

import gift.doamin.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;


    public Wish(User user, Long productId, Integer quantity) {
        this.user = user;
        this.productId = productId;
        this.quantity = quantity;
    }

    protected Wish() {

    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
