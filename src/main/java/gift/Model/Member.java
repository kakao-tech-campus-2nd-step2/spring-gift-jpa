package gift.Model;

import jakarta.persistence.*;

@Entity
@Table(name="userInfo")
public class UserInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="email")
        private String email;

        @Column(name="password")
        private String password;

        @Column(name="role")
        private Role role;

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

        public UserInfo(String email, String password, Role role){
                this.email = email;
                this.password = password;
                this.role = role;
        }
}