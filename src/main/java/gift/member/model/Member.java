package gift.member.model;

import gift.product.model.Product;
import gift.wishlist.model.WishList;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    // member 와 wishlist 간의 일대다 관계 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WishList> wishLists = new ArrayList<>();

    public Member(String email, String encode) {
    }

    public void addWishList(String name, String wishlist_id) {
        Product product = new Product(name); // Product 객체 생성
        WishList wishList = new WishList(this, product);
        wishLists.add(wishList);
    }

    public List<WishList> getWishLists() {
        return new ArrayList<>(wishLists);
    }

    public Long member_id() {
        return member_id;
    }

    // 새 이메일로 업데이트된 Member 인스턴스를 반환
    public Member withEmail(String newEmail) {
        return new Member(newEmail, this.password);
    }

    // 새 비밀번호로 업데이트된 Member 인스턴스를 반환
    public Member withPassword(String newPassword) {
        return new Member(this.email, newPassword);
    }

    public String password() {
        return password;
    }

    public String email() {
        return email;
    }
}