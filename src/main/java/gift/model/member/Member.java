package gift.model.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member {
        @Id
        private String email;
        private String password;

        public Member(String email, String password) {
                this.email = email;
                this.password = password;
        }
        public String getEmail() {
                return email;
        }
        public String getPassword() {
                return password;
        }
}