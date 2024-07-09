package gift.entity;

import gift.dto.MemberDTO;
import gift.dto.WishListDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Column(nullable = false, unique = true)
    @Size(max=255)
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(max=255)
    @Column(nullable = false)
    private String password;

    @Size(max=255)
    @Column(nullable = false)
    private String name;

    @Size(max=255)
    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "member")
    private List<Wish> wishList;

    public Member() { }

    public Member(String email, String password, String name, String role, List<Wish> wishList) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.wishList = wishList;
    }

    public Member(MemberDTO memberDTO) {
        this.email = memberDTO.getEmail();
        this.password = memberDTO.getPassword();
        this.name = memberDTO.getName();
        this.role = memberDTO.getRole();
        this.wishList = memberDTO.getWishList();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Wish> getWishList() {
        return wishList;
    }

    public void setWishList(List<Wish> wishList) {
        this.wishList = wishList;
    }

    public Long getId() {
        return id;
    }


    public Wish addWish(Product product) {
        for (Wish wish : wishList) {
            if (wish.getProduct().equals(product)) {
                wish.incrementQuantity();
                return wish;
            }
        }
        wishList.add(new Wish(this, product, 1));
        return wishList.getLast();
    }

    public boolean setWishQuantity(Product product, Integer value) {
        if (value < 1) {
            return removeWish(product);
        }
        for (Wish wish : wishList) {
            if (wish.getProduct().equals(product)) {
                wish.setQuantity(value);
                return true;
            }
        }
        return false;
    }

    public boolean removeWish(Product product) {
        for (Wish wish : wishList) {
            if (wish.getProduct().equals(product)) {
                return wishList.remove(wish);
            }
        }
        return false;
    }

    public WishListDTO convertToWishListDTO() {
        List<Wish> wishListForView = new ArrayList<>(this.getWishList());
        for (Wish wish : wishListForView) {
            wish.setMember(null); // 개인정보 보호
        }
        return new WishListDTO(wishListForView);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(email, member.email) &&
                Objects.equals(password, member.password) &&
                Objects.equals(name, member.name) &&
                Objects.equals(role, member.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name, role);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", wishList=" + wishList +
                '}';
    }
}
