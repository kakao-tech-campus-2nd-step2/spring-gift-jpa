package gift.model;

import jakarta.persistence.*;

@Entity
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false)
    private Long memberId;

    public Wish() {
    }

    public Wish(Long productId, Long memberId) {
        this.productId = productId;
        this.memberId = memberId;
    }

    public Wish(Long id, Long productId, Long memberId) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getmemberId() {
        return memberId;
    }
}
