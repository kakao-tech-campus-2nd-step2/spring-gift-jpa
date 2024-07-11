package gift.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wish {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private long memberId;
    private long productId;

    public Wish() {}

    public Wish(long memberId, long productId) {
        this.memberId = memberId;
        this.productId = productId;
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
