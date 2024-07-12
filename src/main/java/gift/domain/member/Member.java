package gift.domain.member;

import gift.domain.Wish;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private MemberRole role;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Wish> wishes = new ArrayList<>();

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MemberRole getRole() {
        return role;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public Member(Long id, String name, String email, String password, MemberRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static class MemberBuilder {
        private Long id;
        private String name;
        private String email;
        private String password;
        private MemberRole role;

        public MemberBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MemberBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MemberBuilder email(String email) {
            this.email = email;
            return this;
        }

        public MemberBuilder password(String password) {
            this.password = password;
            return this;
        }

        public MemberBuilder role(MemberRole role) {
            this.role = role;
            return this;
        }

        public Member build() {
            return new Member(id, name, email, password, role);
        }
    }

}
