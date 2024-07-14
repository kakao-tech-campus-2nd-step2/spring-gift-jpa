package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    @Column(nullable = false)
    private String password;

    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    @Size(max = 255)
    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "member")
    private List<Wish> wishList;

    protected Member() {
    }

    public Member(String email, String password, String name, String role, List<Wish> wishList) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.wishList = wishList;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getRole() {
        return role;
    }
    public List<Wish> getWishList() {
        return wishList;
    }
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
}