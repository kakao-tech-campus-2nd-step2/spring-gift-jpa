package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wishlists")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "memberId", nullable = false)
    private long memberId;
    @Column(name = "productName", nullable = false)
    private String productName;
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected WishList() {
    }

    public WishList(String email, long memberId, String productName, long quantity) {
        this.email = email;
        this.memberId = memberId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public WishList(long id, String email, long memberId, String productName, long quantity) {
        this.id = id;
        this.email = email;
        this.memberId = memberId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getproductName() {
        return productName;
    }

    public void setproductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
}
