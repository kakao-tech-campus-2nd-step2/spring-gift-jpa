package gift.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(nullable = false)
    private final Long memberId;

    @Column(nullable = false)
    private final Long productId;

    protected Wish() {
        this(null, null, null);
    }

    public Wish(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Wish(Long memberId, Long productId) {
        this(null, memberId, productId);
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }
}
