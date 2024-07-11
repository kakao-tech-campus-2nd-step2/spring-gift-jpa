package gift.wishes.infrastructure.persistence;

import gift.product.infrastructure.persistence.ProductEntity;
import gift.user.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "wish")
public class WishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    public WishEntity() {
    }

    public WishEntity(Long id, UserEntity user, ProductEntity product) {
        this.id = id;
        this.user = user;
        this.product = product;
    }

    public WishEntity(UserEntity user, ProductEntity product) {
        this.id = 0L;
        this.user = user;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public ProductEntity getProduct() {
        return product;
    }
}
