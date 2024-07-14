package gift.model.product;

import gift.global.validate.ProductNameValidator;
import gift.model.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private String imageUrl;

    protected Product() {
    }

    public Product(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
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

    public static Product create(Long id, String name, Integer price, String imageUrl) {
        ProductNameValidator.isValid(name);
        return new Product(id, name, price, imageUrl);
    }

    public void update(String name, Integer price, String imageUrl) {
        ProductNameValidator.isValid(name);
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

}
