package gift.domain;

import groovy.transform.builder.Builder;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "imageURL", nullable = false)
    private String imageURL;

    protected Product() {}

    public Product(int price, String name, String imageURL) {
        this.price = price;
        this.name = name;
        this.imageURL = imageURL;
    }

    public Product(int id, int price, String name, String imageURL) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getId() {
        return String.valueOf(id);
    }
}