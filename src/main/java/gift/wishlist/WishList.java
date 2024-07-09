package gift.wishlist;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Table(name = "wishlist")
@Entity
public class WishList {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, unique = true, length = 15)
    private long productId;
    @Column(nullable = false)
    @Min(value = 1)
    private int num;

    public WishList() {
    }

    public WishList(String email, long productId, int num) {
        this.email = email;
        this.productId = productId;
        this.num = num;
    }

    public void update(String email, long productId, int num) {
        this.email = email;
        this.productId = productId;
        this.num = num;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public long getProductId() {
        return productId;
    }

    public int getNum() {
        return num;
    }
}
