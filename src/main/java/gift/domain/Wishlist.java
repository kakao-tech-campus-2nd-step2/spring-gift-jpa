package gift.domain;

import jakarta.persistence.*;

@Entity
public class Wishlist {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long productId;

    public Wishlist() {
    }

    public Wishlist(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

}
