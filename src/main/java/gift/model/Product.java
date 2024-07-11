package gift.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private String imageurl;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    @JsonIgnore
    private Wishlist wishlist;

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Product() {
    }

    public Product(ProductDTO product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageurl = product.getImageurl();
    }

    public Product(String name, int price, String imageurl) {
        this.name = name;
        this.price = price;
        this.imageurl = imageurl;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
