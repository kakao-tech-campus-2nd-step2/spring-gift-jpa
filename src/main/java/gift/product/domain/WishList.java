package gift.product.domain;

import gift.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity(name = "wishlist")
public class WishList {

    @Id
    @Column(name = "wishlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<WishListProduct> wishListProducts = new ArrayList<>();

    private LocalDateTime createdAt;

    public WishList() {
    }

    public WishList(User user, ArrayList<WishListProduct> wishListProducts, LocalDateTime createdAt) {
        this.user = user;
        this.wishListProducts = wishListProducts;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.getWishLists().add(this);
    }

    public ArrayList<WishListProduct> getWishListProducts() {
        return wishListProducts;
    }

    public void addWishListProduct(WishListProduct wishListProduct) {
        wishListProducts.add(wishListProduct);
        wishListProduct.setWishList(this);
    }

    public void removeWishListProduct(Long wishListProductId) {
        for (WishListProduct wishListProduct : wishListProducts) {
            if (wishListProduct.getId().equals(wishListProductId)) {
                wishListProducts.remove(wishListProduct);
                wishListProduct.setWishList(null);
                break;
            }
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
