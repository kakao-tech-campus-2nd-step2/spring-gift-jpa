package gift.product;

import gift.wishlist.WishList;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Column(nullable = false)
    private int price;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false, unique = true, length = 15)
    private String name;
    @Column(nullable = false)
    private String imageUrl;
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<WishList> wishes = new ArrayList<>();

    public Product() {
    }

    public Product(String name, int price, String imageUrl) {
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Product(long id, String name, int price, String imageUrl) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void update(String name, int price, String imageUrl) {
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
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

    public List<WishList> getWishes() {
        return wishes;
    }

    public void addWishList(WishList wishList) {
        this.wishes.add(wishList);
        wishList.setProduct(this);
    }

    public void removeWishList(WishList wishList) {
        wishes.remove(wishList);
        wishList.setProduct(null);
    }
}
