package gift.wishlist;

import gift.product.Product;
import gift.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Table(name = "wishlist")
@Entity
public class WishList {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;
    @Column(nullable = false)
    @Min(value = 1)
    private int num;

    public WishList() {
    }

    public WishList(User user, Product product, int num) {
        this.user = user;
        this.product = product;
        this.num = num;
    }

    public void update(int num) {
        this.num = num;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNum() {
        return num;
    }
}
