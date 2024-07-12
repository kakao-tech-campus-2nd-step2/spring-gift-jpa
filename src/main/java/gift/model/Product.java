package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="PRODUCT_TABLE")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name="PRODUCT_NAME",length = 15, nullable = false)
    private String name;

    @Column(name="PRODUCT_PRICE",nullable = false)
    private Integer price;

    @Column(name="PRODUCT_IMAGE_URL",nullable = false)

    private String imageUrl;

    public Product(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product() {

    }

    //setter - 타임리프 사용을 위해 필요
    public void setId(Long id) {
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
