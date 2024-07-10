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

    public Wish(long id, long memberId, long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Wish(long memberId, long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Wish() {
    }

    public long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }
}
