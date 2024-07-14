package gift.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "member")
public class Member {
    @Id
    private String id;

    @Column(name = "password",nullable = false)
    private String password;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<WishList> wishList;

    public Member(){}

    public Member(String id, String password,List<WishList> wishList) {
        this.id = id;
        this.password = password;
        this.wishList = wishList;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
