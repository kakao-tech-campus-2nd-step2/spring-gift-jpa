package gift.doamin.product.entity;

import gift.doamin.product.dto.ProductForm;
import gift.doamin.user.entity.User;
import gift.global.AuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Product extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String imageUrl;

    public Product(User user, String name, Integer price, String imageUrl) {
        this.user = user;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    protected Product() {

    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void updateAll(ProductForm productForm) {
        this.name = productForm.getName();
        this.price = productForm.getPrice();
        this.imageUrl = productForm.getImageUrl();
    }
}
