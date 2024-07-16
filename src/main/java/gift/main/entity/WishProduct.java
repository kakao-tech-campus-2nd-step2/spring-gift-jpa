package gift.main.entity;

import jakarta.persistence.*;

@Entity
public class WishProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prduct_id")
    public Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;


    public WishProduct() {

    }

    public WishProduct(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }
}
