package gift.domain;

public class Wish {
    private Long id;
    private Long memberId;
    private Long productId;

    public Wish(){}
    public Wish(Long productId, Long memberId) {
        this.productId = productId;
        this.memberId = memberId;
    }

    public Long getId(){return this.id;}
    public void setId(Long id) { this.id = id; }
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
