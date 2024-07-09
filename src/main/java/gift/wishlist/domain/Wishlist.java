package gift.wishlist.domain;

import jakarta.persistence.*;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long memberId;

    public Wishlist() {
    }

    public Wishlist(Long id, Long productId, Long memberId) {
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

    public Long getMemberId() {
        return memberId;
    }
}
