package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member {
    @Id
    private String id;

    @Column(name = "password",nullable = false)
    private String password;

    public Member(){}

    public Member(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
