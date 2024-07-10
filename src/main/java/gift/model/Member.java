package gift.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Wish> wishes;


    private Member(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.wishes = builder.wishes;
    }

    public Member() {

    }


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Wish> getWishes() {
        return wishes;
    }


    public static class Builder {
        private Long id;
        private String email;
        private String password;
        private List<Wish> wishes;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder wishes(List<Wish> wishes) {
            this.wishes = wishes;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }


    public static Builder builder() {
        return new Builder();
    }
}
