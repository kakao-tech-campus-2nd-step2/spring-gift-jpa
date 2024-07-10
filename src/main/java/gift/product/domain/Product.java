package gift.product.domain;

import gift.product.exception.ProductNoConferredException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private String imgUrl;

    private Boolean isDeleted;

    protected Product() {
    }

    public Product(Long id, String name, Integer price, String imgUrl) {
        this(id, name, price, imgUrl, Boolean.FALSE);
    }

    public Product(Long id, String name, Integer price, String imgUrl, Boolean isDeleted) {
        checkName(name);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.isDeleted = isDeleted;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    public void delete() {
        this.isDeleted = Boolean.TRUE;
    }

    public boolean isNew() {
        return this.id == null;
    }

    private void checkName(String name) {
        if (name.contains("카카오")) {
            throw new ProductNoConferredException(List.of("카카오"));
        }
    }
}
