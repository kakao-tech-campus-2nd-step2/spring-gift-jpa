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
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "count", nullable = false)
    private Integer count;

    public Wish(Long memberId, String productName, Integer count) {
        this.memberId = memberId;
        this.productName = productName;
        this.count = count;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getCount() {
        return count;
    }
}
