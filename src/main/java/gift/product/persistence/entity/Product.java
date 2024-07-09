package gift.product.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String url;

    public Product(String name, String description, Integer price, String url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }

    public Product() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public void update(String name, String description, Integer price, String url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }
}
