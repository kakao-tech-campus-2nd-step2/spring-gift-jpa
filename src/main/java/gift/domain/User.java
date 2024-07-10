package gift.domain;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String userId;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    Boolean admin;

    protected User(){

    }

    public User(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.admin = false;
    }

    public String getUserId(){
        return userId;
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
    public void setPassword(String password){
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
