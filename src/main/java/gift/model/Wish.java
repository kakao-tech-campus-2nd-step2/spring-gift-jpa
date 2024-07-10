package gift.model;

import jakarta.persistence.*;

@Entity
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "MEMBER_ID")
    private long memberId;

    @Column(name = "PRODUCT_ID")
    private long productId;

    public Wish(long id, long userId, long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Wish(long userId, long productId) {
        this.id = 0;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Wish() {
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }
}
