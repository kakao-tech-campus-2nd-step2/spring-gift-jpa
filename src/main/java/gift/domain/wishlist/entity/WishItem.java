package gift.domain.wishlist.entity;

import gift.domain.product.entity.Product;
import gift.domain.user.entity.User;

public class WishItem {
    Long id;
    User user;
    Product product;

    public WishItem(Long id, User user, Product product) {
        this.id = id;
        this.user = user;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return user.getId();
    }

    public Long getProductId() {
        return product.getId();
    }
}
