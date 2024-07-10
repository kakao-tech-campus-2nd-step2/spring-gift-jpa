package gift.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@NotNull(message = "ID 속성이 누락되었습니다.")
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "희망하는 사람의 정보가 누락되었습니다.")
    private Long memberId;

    @Column(nullable = false)
    @NotNull(message = "희망하는 상품의 정보가 누락되었습니다.")
    private Long productId;

    public Wish() {

    }

    public Wish(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
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
}
