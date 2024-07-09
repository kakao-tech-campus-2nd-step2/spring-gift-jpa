package gift.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Wish> wishes;

    public User() {}

    public User(Long id, String email, String password, Set<Wish> wishes) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.wishes = wishes;
    }

    public Long id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public Set<Wish> wishes() {
        return wishes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (User) obj;
        return Objects.equals(this.id, that.id) &&
            Objects.equals(this.email, that.email) &&
            Objects.equals(this.password, that.password) &&
            Objects.equals(this.wishes, that.wishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, wishes);
    }

    @Override
    public String toString() {
        return "User[" +
            "id=" + id + ", " +
            "email=" + email + ", " +
            "password=" + password + ", " +
            "wishes=" + wishes + ']';
    }

}
