package gift.member.model;

import gift.product.model.Product;
import gift.wishlist.model.WishList;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();
    private WishList wishList;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getMemberId() {
        return member_id;
    }

    /* (동사로 시작) 컨벤션에 맞게 메소드명 수정 */
    public Member updateEmail(String newEmail) {
        return new Member(newEmail, this.password); // 새 이메일로 업데이트된 Member 인스턴스를 반환
    }

    public Member updatePassword(String newPassword) {
        return new Member(this.email, newPassword); // 새 비밀번호로 업데이트된 Member 인스턴스를 반환
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

}