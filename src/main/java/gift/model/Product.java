package gift.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "price")
    private Integer price;
    @NotNull
    @Column(name = "image_url")
    private String imageUrl;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();

    protected Product() {
    }

    public Product(String name, Integer price, String imageUrl) {
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

    public List<ProductOption> getOptions() {
        return options;
    }

    public void updateProductInfo(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
