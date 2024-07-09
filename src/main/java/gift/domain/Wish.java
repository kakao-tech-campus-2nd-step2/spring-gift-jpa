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

    public Wish(Long id, Long productId, Long memberId) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
    }

    public Long getId(){return this.id;}
    public Long getMemberId() {
        return memberId;
    }
    public Long getProductId() {
        return productId;
    }

}
