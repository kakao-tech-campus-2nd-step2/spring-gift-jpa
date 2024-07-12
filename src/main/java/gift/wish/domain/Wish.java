package gift.wish.domain;

import gift.product.domain.Product;
import gift.user.domain.User;
import gift.wish.exception.WishCanNotModifyException;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Product getProduct() {
        return product;
    }

    public void modify(final int amount, final Product product, final User user) {
        checkOwner(user);
        checkProduct(product);
        this.amount = amount;
    }

    private void checkOwner(final User user) {
        if (!this.user.getId()
                .equals(user.getId())) {
            throw new WishCanNotModifyException();
        }
    }

    private void checkProduct(final Product product) {
        if (!this.product.getId()
                .equals(product.getId())) {
            throw new WishCanNotModifyException();
        }
    }

    public boolean isOwner(final Long userId) {
        return this.user.getId().equals(userId);
    }
}
