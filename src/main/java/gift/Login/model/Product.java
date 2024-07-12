package gift.Login.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private long price;
    private String temperatureOption;
    private String cupOption;
    private String sizeOption;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    @JsonIgnore
    private Wishlist wishlist;

    // Default constructor
    public Product() {}

    // Constructor with parameters
    public Product(String name, long price, String temperatureOption, String cupOption, String sizeOption, String imageUrl) {
        this.name = name;
        this.price = price;
        this.temperatureOption = temperatureOption;
        this.cupOption = cupOption;
        this.sizeOption = sizeOption;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getTemperatureOption() {
        return temperatureOption;
    }

    public void setTemperatureOption(String temperatureOption) {
        this.temperatureOption = temperatureOption;
    }

    public String getCupOption() {
        return cupOption;
    }

    public void setCupOption(String cupOption) {
        this.cupOption = cupOption;
    }

    public String getSizeOption() {
        return sizeOption;
    }

    public void setSizeOption(String sizeOption) {
        this.sizeOption = sizeOption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }
}
