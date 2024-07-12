package gift.member.model;

import gift.wishlist.model.WishList;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // member 와 wishlist 간의 일대다 관계 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WishList> wishLists = new HashSet<>();

    public Member(String email, String encode) {
    }

    public void addWishList(String name, String product) {
        WishList wishList = new WishList(this, name, product);
        wishLists.add(wishList);
    }

    public Set<WishList> getWishLists() {
        return wishLists;
    }

    public Long id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }
}