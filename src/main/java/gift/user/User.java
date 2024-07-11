package gift.user;


import gift.wishList.WishList;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "nickname")
    private String nickname;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();


    public User() {
    }


    public User(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickname = nickName;
    }

    public void addWishList(WishList wishList) {
        this.wishLists.add(wishList);
        wishList.setUser(this);
    }

    public void removeWishList(WishList wishList) {
        wishList.setUser(null);
        this.wishLists.remove(wishList);
    }

    public void removeWishLists() {
        for (WishList wishList : wishLists) {
            removeWishList(wishList);
        }
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<WishList> getWishLists() {
        return wishLists;
    }
}
