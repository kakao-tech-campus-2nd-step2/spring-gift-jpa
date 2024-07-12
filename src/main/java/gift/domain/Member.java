package gift.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "member")
public class Member {
    @Id
    private String id;

    @Column(name = "password",nullable = false)
    private String password;

    @OneToMany
    private List<WishList> wishList;

    public Member(){}
    public Member(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
