package gift.entity;

import gift.dto.product.ProductPatchDTO;
import gift.dto.product.ProductRequestDTO;
import jakarta.persistence.*;

@Entity
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private int price;

    @Column(length = 15)
    private String name;

    private String imageUrl;

    public Product() {}

    public Product(int price, String name, String imageUrl) {
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product patch(ProductPatchDTO patch) {
        if (patch.name() != null) {
            this.name = patch.name();
        }

        if (patch.imageUrl() != null) {
            this.imageUrl = patch.imageUrl();
        }

        if (patch.price() != null) {
            this.price = patch.price();
        }

        return this;
    }

    public static Product from(ProductRequestDTO productRequestDTO) {
        return new Product(
                productRequestDTO.price(),
                productRequestDTO.name(),
                productRequestDTO.imageUrl()
        );
    }
}
