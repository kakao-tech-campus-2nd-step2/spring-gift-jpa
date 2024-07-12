package gift.wish.domain;

import gift.product.domain.Product;
import gift.user.domain.User;
import gift.wish.exception.WishCanNotModifyException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SoftDelete;

@Entity
@SoftDelete
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected Wish() {
    }

    public Wish(Long id, Integer amount, Product product, User user) {
        this.id = id;
        this.amount = amount;
        this.product = product;
        this.user = user;
    }

    public Wish(Integer amount, Product product, User user) {
        this(null, amount, product, user);
    }

    public Long getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void modify(final Long userId, final Long productId, final int amount) {
        checkOwner(userId);
        checkProduct(productId);
        this.amount = amount;
    }

    private void checkOwner(final Long userId) {
        if (!this.user.getId().equals(userId)) {
            throw new WishCanNotModifyException();
        }
    }

    private void checkProduct(final Long productId) {
        if (!this.product.getId().equals(productId)) {
            throw new WishCanNotModifyException();
        }
    }

    public boolean isOwner(final Long userId) {
        return this.user.getId().equals(userId);
    }

    public Product getProduct() {
        return product;
    }
}
