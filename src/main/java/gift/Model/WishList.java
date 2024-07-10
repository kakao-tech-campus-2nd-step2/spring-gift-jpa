package gift.Model;

import jakarta.persistence.*;

@Entity
public class WishList {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    Long productId;
    @Column(nullable = false)
    int count;

    protected WishList(){}

    public WishList(String email, Long id, Long productId, int count) {
        this.email = email;
        this.id = id;
        this.productId = productId;
        this.count = count;
    }

    public WishList(String email, Long productId, int count) {
        this.email = email;
        this.productId = productId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
