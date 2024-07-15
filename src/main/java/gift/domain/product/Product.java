package gift.domain.product;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    public Product() {}

    public Product(String name, Long price, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public void updateAdmin(ProductRequestDTO productRequestDTO, String imageUrl) {
        if (productRequestDTO != null) {
            this.name = productRequestDTO.getName();
            this.price = productRequestDTO.getPrice();
            this.description = productRequestDTO.getDescription();
        }
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
    }

    public void update(ProductRequestDTO productRequestDTO) {
        if (productRequestDTO != null) {
            this.name = productRequestDTO.getName();
            this.price = productRequestDTO.getPrice();
            this.description = productRequestDTO.getDescription();
        }
    }
}
