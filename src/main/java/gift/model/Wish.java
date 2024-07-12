package gift.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;

@Entity
@Table(name = "wish")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // 기본 생성자
    protected Wish() {
    }

    // 생성자
    public Wish(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public Long getId() { return id; }

    public User getUser() { return user; }

    public Product getProduct() { return product; }

    public Long getUserId() { return user.getId(); }

    public Long getProductId() { return product.getId(); }
}
