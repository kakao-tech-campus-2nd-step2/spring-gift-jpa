package gift.Model.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import gift.Model.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name="member")
public class MemberEntity {
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

        @OnDelete(action= OnDeleteAction.CASCADE)
        @OneToMany(mappedBy = "member")
        @JsonManagedReference
        private List<WishEntity> wishEntities;

        public MemberEntity(){}

        public MemberEntity(String email, String password, Role role){
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

        public List<WishEntity> getWishes() {
                return wishEntities;
        }

        public void setWishes(List<WishEntity> wishEntities) {
                this.wishEntities = wishEntities;
        }
}