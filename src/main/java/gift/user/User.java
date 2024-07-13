package gift.user;

import gift.wishlist.WishList;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<WishList> wishes = new ArrayList<>();

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<WishList> getWishes() {
        return wishes;
    }

    public void addWishList(WishList wishList) {
        this.wishes.add(wishList);
        wishList.setUser(this);
    }

    public void removeWishList(WishList wishList) {
        wishes.remove(wishList);
        wishList.setUser(null);
    }
}
