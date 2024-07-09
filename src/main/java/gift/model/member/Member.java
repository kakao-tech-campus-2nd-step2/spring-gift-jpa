package gift.model.member;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String password;

        public Member(String email, String password) {
                this.email = email;
                this.password = password;
        }

        public Long getId() { return id;}

        public String getEmail() {
                return email;
        }

        public String getPassword() {
                return password;
        }
}