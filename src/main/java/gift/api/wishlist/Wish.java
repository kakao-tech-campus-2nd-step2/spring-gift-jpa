package gift.api.wishlist;

import gift.api.member.Member;
import gift.api.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(WishId.class)
public class Wish {
    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;
    @Column(nullable = false)
    private Integer quantity;

    protected Wish() {
    }

    public Wish(Member memberId, Product productId, Integer quantity) {
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void updateQuantity(Integer quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException();
        }
        this.quantity = quantity;
    }

    public Member getMemberId() {
        return memberId;
    }

    public Product getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
