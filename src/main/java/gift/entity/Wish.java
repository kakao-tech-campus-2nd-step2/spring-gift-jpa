package gift.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    Long memberId;
    @NotNull
    Long productId;
    @NotNull
    Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    Product product;

    public Wish(Long memberId, Long productId, Integer amount) {
        this.memberId = memberId;
        this.productId = productId;
        this.amount = amount;
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

    public Integer getAmount() {
        return amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
