package gift.domain;


import gift.dto.ProductRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_tb")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String imageUrl;

    public Product() {
        this.id = -1;
        this.name = "";
        this.price = 0;
        this.imageUrl = "";
    }

    public Product(long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = Math.max(price, 0);
        this.imageUrl = imageUrl;
    }

    public Product(String name, int price, String imageUrl) {
        this.id = -1;
        this.name = name;
        this.price = Math.max(price, 0);
        this.imageUrl = imageUrl;
    }

    public static Product fromEntity(ProductRequestDTO requestDTO) {
        return new Product(requestDTO.getName(), requestDTO.getPrice(), requestDTO.getImageUrl());
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setId(long id) {
        this.id = id;
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
}

