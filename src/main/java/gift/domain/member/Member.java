package gift.domain.member;

import gift.domain.wish.Wish;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private int role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishList = new ArrayList<>();

    public Member(String email, String name, String password, int role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Member(Long id, String email, String name, String password, int role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public List<Wish> getWishList() {
        return wishList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMatch(String password) {
        return password.equals(this.password);
    }

    public void addWish(Wish wish) {
        wishList.add(wish);
    }

    public void deleteWish(Long productId) {
        Wish target = this.wishList.stream()
                .filter(w -> w.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PRODUCT));
        wishList.remove(target);
    }
}
