package gift.wishList;

import gift.product.Product;
import gift.user.User;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "WISHLISTS")
public class WishList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Product product;
    @Column(name = "count")
    long count;

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
