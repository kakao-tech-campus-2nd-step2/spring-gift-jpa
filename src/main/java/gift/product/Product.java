package gift.product;

import gift.wishList.WishList;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "price")
    Long price;
    @Column(name = "imageUrl")
    String imageUrl;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product", orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();

    public void addWishList(WishList wishList) {
        this.wishLists.add(wishList);
        wishList.setProduct(this);
    }

    public void removeWishList(WishList wishList) {
        wishList.setProduct(null);
        this.wishLists.remove(wishList);
    }

    public void removeWishLists() {
        Iterator<WishList> iterator = this.wishLists.iterator();

        while (iterator.hasNext()) {
            WishList wishList = iterator.next();

            wishList.setProduct(null);
            iterator.remove();
        }
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product(String name, Long price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(Long id, String name, Long price, String imageUrl, List<WishList> wishLists) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.wishLists = wishLists;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<WishList> getWishLists() {
        return wishLists;
    }
}
