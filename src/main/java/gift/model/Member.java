package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER_TABLE")

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name="MEMBER_EMAIL",unique = true, nullable = false)
    private String email;

    @Column(name="MEMBER_PASSWORD",nullable = false)
    private String password;

    @Column(name="MEMBER_ROLE",nullable = false)

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String token;


    @OneToMany(mappedBy = "member")
    private List<Wish> wishList = new ArrayList<>();
    public Member(Long id, String email, String password, MemberRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public MemberRole getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public void addProduct(Product product) {
        wishList.add(new Wish(this,product));
    }

    public void delProduct(Product product) {
        wishList.remove(new Wish(this,product));
    }

    public void updateProductCount(Product product,int count) {
        wishList.stream().findAny().ifPresent(wish -> {
            wish.setValue(count);
        });
    }

    public List<Wish> getWishList() {
        return wishList;
    }

}
