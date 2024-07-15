package gift.member.model;

import gift.product.model.Product;
import gift.wishlist.model.WishList;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long member_id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private String email;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private String password;

    // member 와 wishlist 간의 일대다 관계 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WishList> wishLists = new ArrayList<>();

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member() {
    }

    public void addWishList(String name, String wishlist_id) {
        Product product = new Product(name); // Product 객체 생성
        WishList wishList = new WishList(this, product);
        wishLists.add(wishList);
    }

    public List<WishList> getWishLists() {
        return new ArrayList<>(wishLists);
    }

    public Long getMemberId() {
        return member_id;
    }

    // 새 이메일로 업데이트된 Member 인스턴스를 반환
    public void withEmail(String newEmail) {
        new Member(newEmail, this.password);
    }

    // 새 비밀번호로 업데이트된 Member 인스턴스를 반환
    public void withPassword(String newPassword) {
        new Member(this.email, newPassword);
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}