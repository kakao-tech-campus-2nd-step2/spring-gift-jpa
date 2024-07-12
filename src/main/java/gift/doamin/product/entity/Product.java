package gift.doamin.product.entity;

import gift.doamin.product.dto.ProductParam;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String imageUrl;

    public Product(Long userId, String name, Integer price, String imageUrl) {
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    protected Product() {

    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
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

    public void updateAll(ProductParam productParam) {
        this.name = productParam.getName();
        this.price = productParam.getPrice();
        this.imageUrl = productParam.getImageUrl();
    }
}
