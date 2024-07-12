package gift.model.user;

import gift.model.wishList.WishItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WishItem> wishItemList = new ArrayList<>();


    public User() {
    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.password = password;
        this.email = email;
        wishItemList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO toUserDTO() {
        return new UserDTO(id, password, email);
    }

    public List<WishItem> getWishItemList() {
        return wishItemList;
    }
}