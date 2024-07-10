package gift.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "userInfo",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Wish> wishs = new ArrayList<>();

    public UserInfo() {
    }

    public UserInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() { return id; }

    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) { this.id = id; }

    public void setEmail(String email) { this.email = email; }
}
