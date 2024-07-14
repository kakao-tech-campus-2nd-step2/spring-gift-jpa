package gift.api.wishlist;

import gift.api.member.Member;
import gift.api.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(WishId.class)
public class Wish {
    @Id
    @ManyToOne
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_wish_member_id_ref_member_id"))
    private Member member;
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_wish_product_id_ref_product_id"))
    private Product product;
    @Column(nullable = false)
    private Integer quantity;

    protected Wish() {
    }

    public Wish(Member member, Product product, Integer quantity) {
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public void updateQuantity(Integer quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException();
        }
        this.quantity = quantity;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
