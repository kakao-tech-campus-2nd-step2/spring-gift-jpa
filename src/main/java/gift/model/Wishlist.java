package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wish")
public class Wishlist{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private String userEmail;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    protected Wishlist(){}

    public Wishlist(Long id, String userEmail, Long productId) {
        this.id = id;
        this.userEmail = userEmail;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
            "id=" + id +
            ", userEmail='" + userEmail + '\'' +
            ", productId=" + productId +
            '}';
    }
}