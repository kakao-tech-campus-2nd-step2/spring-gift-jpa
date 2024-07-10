package gift.wishList;

import gift.product.Product;
import gift.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "WISHLISTS")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "count")
    private long count;

    public WishList(long count) {
        this.count = count;
    }


    public WishList() {

    }

    public WishList(long id, User user, Product product, long count) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
