package gift.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist_items")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_number", nullable = false)
    private int productNumber;

    public Wish() {}

    public Wish(Long memberId, Long productId, int productNumber) {
        this.memberId = memberId;
        this.productId = productId;
        this.productNumber = productNumber;
    }

    public Wish(Long id, Long memberId, Long productId, int productNumber) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.productNumber = productNumber;
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

    public int getProductNumber() {
        return productNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }
}
