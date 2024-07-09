package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wish")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "count", nullable = false)
    private Integer count;

    public Wish(Long memberId, Long productId, Integer count) {
        this.memberId = memberId;
        this.productId = productId;
        this.count = count;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
