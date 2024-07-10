package gift.model;

import jakarta.persistence.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wish wish = (Wish) o;
        return memberId == wish.memberId && productId == wish.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, productId);
    }
}
