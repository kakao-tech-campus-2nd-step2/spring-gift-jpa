package gift.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true, nullable=false)

    private String email;
    @Column(nullable = false)
    private String password;


    @OneToMany(mappedBy = "user")
    List<WishList> wishlist = new ArrayList<>();

    public User() {
    }

    public void addWishlist(WishList wishlist){
        this.wishlist.add(wishlist);
    }


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
