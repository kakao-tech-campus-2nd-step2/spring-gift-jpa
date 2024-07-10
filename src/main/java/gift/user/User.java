package gift.user;


import gift.wishList.WishList;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "email", unique = true)
    String email;
    @Column(name = "password")
    String password;
    @Column(name = "nickname")
    String nickname;
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
        Iterator<WishList> iterator = this.wishLists.iterator();

        while (iterator.hasNext()) {
            WishList wishList = iterator.next();

            wishList.setUser(null);
            iterator.remove();
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
