package gift.product.domain;

import gift.product.exception.ProductNoConferredException;
import gift.wish.domain.Wish;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.SoftDelete;

@Entity
@SoftDelete
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

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<Wish> wishes = new ArrayList<>();

    protected Product() {
    }

    public Product(String name, Integer price, String imgUrl) {
        this(null, name, price, imgUrl);
    }

    public Product(Long id, String name, Integer price, String imgUrl) {
        checkName(name);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
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

    public void modify(String name, Integer price, String imgUrl) {
        checkName(name);
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    private void checkName(String name) {
        if (name.contains("카카오")) {
            throw new ProductNoConferredException(List.of("카카오"));
        }
    }
}
