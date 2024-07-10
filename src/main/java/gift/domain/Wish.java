package gift.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wishlist")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long productId;

    public Wish() {}

    public Wish(Long wishId, Long memberId, Long productId) {
        this.wishId = wishId;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getWishId() {
        return wishId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


}
