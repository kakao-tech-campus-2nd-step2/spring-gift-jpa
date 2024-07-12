package gift.Model;

import jakarta.persistence.*;

@Entity
public class Wish {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name= "member_id", nullable = false)
    Long memberId;
    @Column(name= "product_id",nullable = false)
    Long productId;
    @Column(nullable = false)
    int count;

    protected Wish(){}

    public Wish(Long id, Long memberId, Long productId, int count) {
        this.memberId = memberId;
        this.id = id;
        this.productId = productId;
        this.count = count;
    }

    public Wish(Long memberId, Long productId, int count) {
        this.memberId = memberId;
        this.productId = productId;
        this.count = count;
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

    public int getCount() {
        return count;
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

    public void setCount(int count) {
        this.count = count;
    }
}
