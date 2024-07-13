package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "wishes")
public class Wish {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Positive
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_wishes_user_id_ref_users_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_wishes_product_id_ref_products_id"))
    private Product product;

    protected Wish() {
    }

    private Wish(Builder builder) {
        this.id = builder.id;
        this.quantity = builder.quantity;
        this.user = builder.user;
        this.product = builder.product;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public void changeQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isQuantityZero() {
        return quantity <= 0;
    }

    public static class Builder {

        private Long id;
        private Integer quantity;
        private User user;
        private Product product;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Wish build() {
            return new Wish(this);
        }

    }

}
