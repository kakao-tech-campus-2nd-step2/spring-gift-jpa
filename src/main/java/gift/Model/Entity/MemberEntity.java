package gift.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="member")
public class Member {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="email")
        private String email;

        @Column(name="password")
        private String password;

        @Enumerated(EnumType.STRING)
        @Column(name="role")
        private Role role;

        @OneToMany(mappedBy = "member")
        @JsonManagedReference
        private List<Wish> wishes;

        public Member(){}

        public Member(String email, String password, Role role){
                this.email = email;
                this.password = password;
                this.role = role;
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

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Role getRole() {
                return role;
        }

        public void setRole(Role role) {
                this.role = role;
        }

        public List<Wish> getWishes() {
                return wishes;
        }

        public void setWishes(List<Wish> wishes) {
                this.wishes = wishes;
        }
}