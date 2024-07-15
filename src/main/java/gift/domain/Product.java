package gift.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long product_id;

    @Column(nullable = false, length = 15)
    String name;

    @Column(nullable = false)
    Integer price;

    @Column(nullable = false)
    String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishes = new ArrayList<>();


    public Product(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void setId(long andIncrement) {
        this.product_id = andIncrement;
    }

    public void setName(Object name) {
        this.name = name.toString();
    }

    public void setPrice(Object price) {
        this.price = (Integer) price;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl.toString();
    }

    public Long getId() {
        return product_id;
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
}
