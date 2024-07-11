package gift.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    long id;

    String name;

    long price;

    @Column(name = "imageurl")
    String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Wishlist> wishlist = new ArrayList<>();

    public Product() {
    }

    public Product(ProductDto productDto) {
        this(productDto.getId(), productDto.getName(), productDto.getPrice(),
            productDto.getImageUrl());
    }

    public Product(long id, String name, long price, String imageUrl) {
        this.id = id;
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

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Wishlist> getWishlist() {
        return wishlist;
    }
}
