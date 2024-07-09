package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "wishes")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Wish() {}

    public Wish(Long id, Integer quantity, User user, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.user = user;
        this.product = product;
    }

    public Long id() {
        return id;
    }

    public Integer quantity() {
        return quantity;
    }

    public User user() {
        return user;
    }

    public Product product() {
        return product;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Wish) obj;
        return Objects.equals(this.id, that.id) &&
            Objects.equals(this.quantity, that.quantity) &&
            Objects.equals(this.user, that.user) &&
            Objects.equals(this.product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, user, product);
    }

    @Override
    public String toString() {
        return "Wish[" +
            "id=" + id + ", " +
            "quantity=" + quantity + ", " +
            "user=" + user + ", " +
            "product=" + product + ']';
    }


}
