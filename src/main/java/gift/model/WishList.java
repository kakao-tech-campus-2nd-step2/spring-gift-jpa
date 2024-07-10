package gift.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public WishList(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public WishList() {
    }

    public Long getId() {
        return id;
    }

    public String getUserEmail() {
        return user != null ? user.getEmail() : null;
    }

    public void setUserEmail(String userEmail) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setEmail(userEmail, this.user.getPassword());
    }

    public Long getProductId() {
        return product != null ? product.getId() : null;
    }

    public void setProductId(Long productId) {
        if (this.product == null) {
            this.product = new Product();
        }
        this.product.setId(productId);
    }
}
