package gift.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    public Wishlist() {
    }

    public Wishlist(String email, Long productId) {
        this.email = email;
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public Long getProductId() {
        return productId;
    }
}
