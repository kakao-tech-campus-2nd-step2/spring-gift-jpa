package gift.main.entity;

import gift.main.dto.ProductRequest;
import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "decimal(10,2) check (price >= 0)")
    private int price;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;


    public Product() {

    }

    public Product(ProductRequest productRequest, User seller) {
        this.name = productRequest.name();
        this.price = productRequest.price();
        this.imageUrl = productRequest.imageUrl();
        this.seller = seller;
    }

    public Product(String name, int price, String imageUrl, User seller) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.seller = seller;
    }

    public void updateValue(ProductRequest productRequest) {
        this.name = productRequest.name();
        this.price = productRequest.price();
        this.imageUrl = productRequest.imageUrl();
    }

    public void updateValue(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public long getId() {
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

    public String getSellerName() {
        return seller.getName();
    }

}
