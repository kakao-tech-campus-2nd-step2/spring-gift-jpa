package gift.api.wishlist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(WishId.class)
public class Wish {
    @Id
    private Long memberId;
    @Id
    private Long productId;
    @Column(nullable = false)
    private Integer quantity;

    protected Wish() {
    }

    public Wish(Long memberId, WishRequest wishRequest) {
        this.memberId = memberId;
        this.productId = wishRequest.productId();
        this.quantity = wishRequest.quantity();
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
